/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import SubjectsComponentsPage from './subjects-my-suffix.page-object';
import { SubjectsDeleteDialog } from './subjects-my-suffix.page-object';
import SubjectsUpdatePage from './subjects-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Subjects e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let subjectsUpdatePage: SubjectsUpdatePage;
  let subjectsComponentsPage: SubjectsComponentsPage;
  let subjectsDeleteDialog: SubjectsDeleteDialog;

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

  it('should load Subjects', async () => {
    await navBarPage.getEntityPage('subjects-my-suffix');
    subjectsComponentsPage = new SubjectsComponentsPage();
    expect(await subjectsComponentsPage.getTitle().getText()).to.match(/Subjects/);
  });

  it('should load create Subjects page', async () => {
    await subjectsComponentsPage.clickOnCreateButton();
    subjectsUpdatePage = new SubjectsUpdatePage();
    expect(await subjectsUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.subjects.home.createOrEditLabel/);
    await subjectsUpdatePage.cancel();
  });

  it('should create and save Subjects', async () => {
    async function createSubjects() {
      await subjectsComponentsPage.clickOnCreateButton();
      await subjectsUpdatePage.setSubjectCodeInput('subjectCode');
      expect(await subjectsUpdatePage.getSubjectCodeInput()).to.match(/subjectCode/);
      await subjectsUpdatePage.setSubjectTitleInput('subjectTitle');
      expect(await subjectsUpdatePage.getSubjectTitleInput()).to.match(/subjectTitle/);
      await waitUntilDisplayed(subjectsUpdatePage.getSaveButton());
      await subjectsUpdatePage.save();
      await waitUntilHidden(subjectsUpdatePage.getSaveButton());
      expect(await subjectsUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createSubjects();
    await subjectsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await subjectsComponentsPage.countDeleteButtons();
    await createSubjects();

    await subjectsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await subjectsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Subjects', async () => {
    await subjectsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await subjectsComponentsPage.countDeleteButtons();
    await subjectsComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    subjectsDeleteDialog = new SubjectsDeleteDialog();
    expect(await subjectsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.subjects.delete.question/);
    await subjectsDeleteDialog.clickOnConfirmButton();

    await subjectsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await subjectsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
