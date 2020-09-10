/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CenterHeadComponentsPage from './center-head-my-suffix.page-object';
import { CenterHeadDeleteDialog } from './center-head-my-suffix.page-object';
import CenterHeadUpdatePage from './center-head-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('CenterHead e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let centerHeadUpdatePage: CenterHeadUpdatePage;
  let centerHeadComponentsPage: CenterHeadComponentsPage;
  let centerHeadDeleteDialog: CenterHeadDeleteDialog;

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

  it('should load CenterHeads', async () => {
    await navBarPage.getEntityPage('center-head-my-suffix');
    centerHeadComponentsPage = new CenterHeadComponentsPage();
    expect(await centerHeadComponentsPage.getTitle().getText()).to.match(/Center Heads/);
  });

  it('should load create CenterHead page', async () => {
    await centerHeadComponentsPage.clickOnCreateButton();
    centerHeadUpdatePage = new CenterHeadUpdatePage();
    expect(await centerHeadUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.centerHead.home.createOrEditLabel/);
    await centerHeadUpdatePage.cancel();
  });

  it('should create and save CenterHeads', async () => {
    async function createCenterHead() {
      await centerHeadComponentsPage.clickOnCreateButton();
      await centerHeadUpdatePage.centerheadSelectLastOption();
      // centerHeadUpdatePage.centerSelectLastOption();
      await waitUntilDisplayed(centerHeadUpdatePage.getSaveButton());
      await centerHeadUpdatePage.save();
      await waitUntilHidden(centerHeadUpdatePage.getSaveButton());
      expect(await centerHeadUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createCenterHead();
    await centerHeadComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await centerHeadComponentsPage.countDeleteButtons();
    await createCenterHead();

    await centerHeadComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await centerHeadComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last CenterHead', async () => {
    await centerHeadComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await centerHeadComponentsPage.countDeleteButtons();
    await centerHeadComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    centerHeadDeleteDialog = new CenterHeadDeleteDialog();
    expect(await centerHeadDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.centerHead.delete.question/);
    await centerHeadDeleteDialog.clickOnConfirmButton();

    await centerHeadComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await centerHeadComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
