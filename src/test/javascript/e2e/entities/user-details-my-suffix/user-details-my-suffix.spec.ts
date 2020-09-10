/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import UserDetailsComponentsPage from './user-details-my-suffix.page-object';
import { UserDetailsDeleteDialog } from './user-details-my-suffix.page-object';
import UserDetailsUpdatePage from './user-details-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('UserDetails e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userDetailsUpdatePage: UserDetailsUpdatePage;
  let userDetailsComponentsPage: UserDetailsComponentsPage;
  let userDetailsDeleteDialog: UserDetailsDeleteDialog;

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

  it('should load UserDetails', async () => {
    await navBarPage.getEntityPage('user-details-my-suffix');
    userDetailsComponentsPage = new UserDetailsComponentsPage();
    expect(await userDetailsComponentsPage.getTitle().getText()).to.match(/User Details/);
  });

  it('should load create UserDetails page', async () => {
    await userDetailsComponentsPage.clickOnCreateButton();
    userDetailsUpdatePage = new UserDetailsUpdatePage();
    expect(await userDetailsUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.userDetails.home.createOrEditLabel/);
    await userDetailsUpdatePage.cancel();
  });

  it('should create and save UserDetails', async () => {
    async function createUserDetails() {
      await userDetailsComponentsPage.clickOnCreateButton();
      await userDetailsUpdatePage.setMobileNoInput('mobileNo');
      expect(await userDetailsUpdatePage.getMobileNoInput()).to.match(/mobileNo/);
      await userDetailsUpdatePage.setDobInput('01-01-2001');
      expect(await userDetailsUpdatePage.getDobInput()).to.eq('2001-01-01');
      await userDetailsUpdatePage.setHouseNoInput('houseNo');
      expect(await userDetailsUpdatePage.getHouseNoInput()).to.match(/houseNo/);
      await userDetailsUpdatePage.setStreetNoInput('5');
      expect(await userDetailsUpdatePage.getStreetNoInput()).to.eq('5');
      await userDetailsUpdatePage.citySelectLastOption();
      await userDetailsUpdatePage.stateSelectLastOption();
      await userDetailsUpdatePage.setPincodeInput('5');
      expect(await userDetailsUpdatePage.getPincodeInput()).to.eq('5');
      await userDetailsUpdatePage.userSelectLastOption();
      await waitUntilDisplayed(userDetailsUpdatePage.getSaveButton());
      await userDetailsUpdatePage.save();
      await waitUntilHidden(userDetailsUpdatePage.getSaveButton());
      expect(await userDetailsUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createUserDetails();
    await userDetailsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await userDetailsComponentsPage.countDeleteButtons();
    await createUserDetails();

    await userDetailsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await userDetailsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last UserDetails', async () => {
    await userDetailsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await userDetailsComponentsPage.countDeleteButtons();
    await userDetailsComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    userDetailsDeleteDialog = new UserDetailsDeleteDialog();
    expect(await userDetailsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.userDetails.delete.question/);
    await userDetailsDeleteDialog.clickOnConfirmButton();

    await userDetailsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await userDetailsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
