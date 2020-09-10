/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import UserPreferencesComponentsPage from './user-preferences-my-suffix.page-object';
import { UserPreferencesDeleteDialog } from './user-preferences-my-suffix.page-object';
import UserPreferencesUpdatePage from './user-preferences-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('UserPreferences e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userPreferencesUpdatePage: UserPreferencesUpdatePage;
  let userPreferencesComponentsPage: UserPreferencesComponentsPage;
  let userPreferencesDeleteDialog: UserPreferencesDeleteDialog;

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

  it('should load UserPreferences', async () => {
    await navBarPage.getEntityPage('user-preferences-my-suffix');
    userPreferencesComponentsPage = new UserPreferencesComponentsPage();
    expect(await userPreferencesComponentsPage.getTitle().getText()).to.match(/User Preferences/);
  });

  it('should load create UserPreferences page', async () => {
    await userPreferencesComponentsPage.clickOnCreateButton();
    userPreferencesUpdatePage = new UserPreferencesUpdatePage();
    expect(await userPreferencesUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /risingArjunApp.userPreferences.home.createOrEditLabel/
    );
    await userPreferencesUpdatePage.cancel();
  });

  it('should create and save UserPreferences', async () => {
    async function createUserPreferences() {
      await userPreferencesComponentsPage.clickOnCreateButton();
      await userPreferencesUpdatePage.setThemeInput('theme');
      expect(await userPreferencesUpdatePage.getThemeInput()).to.match(/theme/);
      await userPreferencesUpdatePage.userSelectLastOption();
      await waitUntilDisplayed(userPreferencesUpdatePage.getSaveButton());
      await userPreferencesUpdatePage.save();
      await waitUntilHidden(userPreferencesUpdatePage.getSaveButton());
      expect(await userPreferencesUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createUserPreferences();
    await userPreferencesComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await userPreferencesComponentsPage.countDeleteButtons();
    await createUserPreferences();

    await userPreferencesComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await userPreferencesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last UserPreferences', async () => {
    await userPreferencesComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await userPreferencesComponentsPage.countDeleteButtons();
    await userPreferencesComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    userPreferencesDeleteDialog = new UserPreferencesDeleteDialog();
    expect(await userPreferencesDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /risingArjunApp.userPreferences.delete.question/
    );
    await userPreferencesDeleteDialog.clickOnConfirmButton();

    await userPreferencesComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await userPreferencesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
