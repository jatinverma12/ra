/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import StudentsSubjectsComponentsPage from './students-subjects-my-suffix.page-object';
import { StudentsSubjectsDeleteDialog } from './students-subjects-my-suffix.page-object';
import StudentsSubjectsUpdatePage from './students-subjects-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('StudentsSubjects e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let studentsSubjectsUpdatePage: StudentsSubjectsUpdatePage;
  let studentsSubjectsComponentsPage: StudentsSubjectsComponentsPage;
  let studentsSubjectsDeleteDialog: StudentsSubjectsDeleteDialog;

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

  it('should load StudentsSubjects', async () => {
    await navBarPage.getEntityPage('students-subjects-my-suffix');
    studentsSubjectsComponentsPage = new StudentsSubjectsComponentsPage();
    expect(await studentsSubjectsComponentsPage.getTitle().getText()).to.match(/Students Subjects/);
  });

  it('should load create StudentsSubjects page', async () => {
    await studentsSubjectsComponentsPage.clickOnCreateButton();
    studentsSubjectsUpdatePage = new StudentsSubjectsUpdatePage();
    expect(await studentsSubjectsUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /risingArjunApp.studentsSubjects.home.createOrEditLabel/
    );
    await studentsSubjectsUpdatePage.cancel();
  });

  it('should create and save StudentsSubjects', async () => {
    async function createStudentsSubjects() {
      await studentsSubjectsComponentsPage.clickOnCreateButton();
      await studentsSubjectsUpdatePage.monthSelectLastOption();
      await studentsSubjectsUpdatePage.registrationnoSelectLastOption();
      await studentsSubjectsUpdatePage.sessionSelectLastOption();
      // studentsSubjectsUpdatePage.subjectsSelectLastOption();
      // studentsSubjectsUpdatePage.courseSelectLastOption();
      await waitUntilDisplayed(studentsSubjectsUpdatePage.getSaveButton());
      await studentsSubjectsUpdatePage.save();
      await waitUntilHidden(studentsSubjectsUpdatePage.getSaveButton());
      expect(await studentsSubjectsUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createStudentsSubjects();
    await studentsSubjectsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await studentsSubjectsComponentsPage.countDeleteButtons();
    await createStudentsSubjects();

    await studentsSubjectsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await studentsSubjectsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last StudentsSubjects', async () => {
    await studentsSubjectsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await studentsSubjectsComponentsPage.countDeleteButtons();
    await studentsSubjectsComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    studentsSubjectsDeleteDialog = new StudentsSubjectsDeleteDialog();
    expect(await studentsSubjectsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /risingArjunApp.studentsSubjects.delete.question/
    );
    await studentsSubjectsDeleteDialog.clickOnConfirmButton();

    await studentsSubjectsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await studentsSubjectsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
