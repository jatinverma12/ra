/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CentersComponentsPage from './centers-my-suffix.page-object';
import { CentersDeleteDialog } from './centers-my-suffix.page-object';
import CentersUpdatePage from './centers-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Centers e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let centersUpdatePage: CentersUpdatePage;
  let centersComponentsPage: CentersComponentsPage;
  let centersDeleteDialog: CentersDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
  });

  it('should load Centers', async () => {
    await navBarPage.getEntityPage('centers-my-suffix');
    centersComponentsPage = new CentersComponentsPage();
    expect(await centersComponentsPage.getTitle().getText()).to.match(/Centers/);
  });

  it('should load create Centers page', async () => {
    await centersComponentsPage.clickOnCreateButton();
    centersUpdatePage = new CentersUpdatePage();
    expect(await centersUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.centers.home.createOrEditLabel/);
    await centersUpdatePage.cancel();
  });

  it('should create and save Centers', async () => {
    async function createCenters() {
      await centersComponentsPage.clickOnCreateButton();
      await centersUpdatePage.setCenterCodeInput('centerCode');
      expect(await centersUpdatePage.getCenterCodeInput()).to.match(/centerCode/);
      await centersUpdatePage.setCenterTitleInput('centerTitle');
      expect(await centersUpdatePage.getCenterTitleInput()).to.match(/centerTitle/);
      await centersUpdatePage.setStreetNoInput('5');
      expect(await centersUpdatePage.getStreetNoInput()).to.eq('5');
      await centersUpdatePage.citySelectLastOption();
      await centersUpdatePage.stateSelectLastOption();
      await centersUpdatePage.setPincodeInput('5');
      expect(await centersUpdatePage.getPincodeInput()).to.eq('5');
      await waitUntilDisplayed(centersUpdatePage.getSaveButton());
      await centersUpdatePage.save();
      await waitUntilHidden(centersUpdatePage.getSaveButton());
      expect(await centersUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createCenters();
    await centersComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await centersComponentsPage.countDeleteButtons();
    await createCenters();

    await centersComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await centersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Centers', async () => {
    await centersComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await centersComponentsPage.countDeleteButtons();
    await centersComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    centersDeleteDialog = new CentersDeleteDialog();
    expect(await centersDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.centers.delete.question/);
    await centersDeleteDialog.clickOnConfirmButton();

    await centersComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await centersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
