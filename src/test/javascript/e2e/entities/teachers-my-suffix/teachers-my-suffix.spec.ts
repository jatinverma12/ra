/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TeachersComponentsPage from './teachers-my-suffix.page-object';
import { TeachersDeleteDialog } from './teachers-my-suffix.page-object';
import TeachersUpdatePage from './teachers-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Teachers e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let teachersUpdatePage: TeachersUpdatePage;
  let teachersComponentsPage: TeachersComponentsPage;
  let teachersDeleteDialog: TeachersDeleteDialog;

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

  it('should load Teachers', async () => {
    await navBarPage.getEntityPage('teachers-my-suffix');
    teachersComponentsPage = new TeachersComponentsPage();
    expect(await teachersComponentsPage.getTitle().getText()).to.match(/Teachers/);
  });

  it('should load create Teachers page', async () => {
    await teachersComponentsPage.clickOnCreateButton();
    teachersUpdatePage = new TeachersUpdatePage();
    expect(await teachersUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.teachers.home.createOrEditLabel/);
    await teachersUpdatePage.cancel();
  });

  it('should create and save Teachers', async () => {
    async function createTeachers() {
      await teachersComponentsPage.clickOnCreateButton();
      await teachersUpdatePage.teacherSelectLastOption();
      // teachersUpdatePage.subjectsSelectLastOption();
      // teachersUpdatePage.coursesSelectLastOption();
      await waitUntilDisplayed(teachersUpdatePage.getSaveButton());
      await teachersUpdatePage.save();
      await waitUntilHidden(teachersUpdatePage.getSaveButton());
      expect(await teachersUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createTeachers();
    await teachersComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await teachersComponentsPage.countDeleteButtons();
    await createTeachers();

    await teachersComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await teachersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Teachers', async () => {
    await teachersComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await teachersComponentsPage.countDeleteButtons();
    await teachersComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    teachersDeleteDialog = new TeachersDeleteDialog();
    expect(await teachersDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.teachers.delete.question/);
    await teachersDeleteDialog.clickOnConfirmButton();

    await teachersComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await teachersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
