/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import StudentsComponentsPage from './students-my-suffix.page-object';
import { StudentsDeleteDialog } from './students-my-suffix.page-object';
import StudentsUpdatePage from './students-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('Students e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let studentsUpdatePage: StudentsUpdatePage;
  let studentsComponentsPage: StudentsComponentsPage;
  let studentsDeleteDialog: StudentsDeleteDialog;
  const fileToUpload = '../../../../../../src/main/webapp/static/images/logo-jhipster.png';
  const absolutePath = path.resolve(__dirname, fileToUpload);

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

  it('should load Students', async () => {
    await navBarPage.getEntityPage('students-my-suffix');
    studentsComponentsPage = new StudentsComponentsPage();
    expect(await studentsComponentsPage.getTitle().getText()).to.match(/Students/);
  });

  it('should load create Students page', async () => {
    await studentsComponentsPage.clickOnCreateButton();
    studentsUpdatePage = new StudentsUpdatePage();
    expect(await studentsUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.students.home.createOrEditLabel/);
    await studentsUpdatePage.cancel();
  });

  it('should create and save Students', async () => {
    async function createStudents() {
      await studentsComponentsPage.clickOnCreateButton();
      await studentsUpdatePage.setStudentRegIdInput('studentRegId');
      expect(await studentsUpdatePage.getStudentRegIdInput()).to.match(/studentRegId/);
      await studentsUpdatePage.setRegistrationFormInput(absolutePath);
      await studentsUpdatePage.setParentMobNo1Input('parentMobNo1');
      expect(await studentsUpdatePage.getParentMobNo1Input()).to.match(/parentMobNo1/);
      await studentsUpdatePage.setParentMobNo2Input('parentMobNo2');
      expect(await studentsUpdatePage.getParentMobNo2Input()).to.match(/parentMobNo2/);
      await studentsUpdatePage.setParentEmailIdInput('parentEmailId');
      expect(await studentsUpdatePage.getParentEmailIdInput()).to.match(/parentEmailId/);
      await studentsUpdatePage.studentStatusSelectLastOption();
      await studentsUpdatePage.leavingReasonSelectLastOption();
      await studentsUpdatePage.infoSourceSelectLastOption();
      await studentsUpdatePage.userSelectLastOption();
      // studentsUpdatePage.courseSelectLastOption();
      await waitUntilDisplayed(studentsUpdatePage.getSaveButton());
      await studentsUpdatePage.save();
      await waitUntilHidden(studentsUpdatePage.getSaveButton());
      expect(await studentsUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createStudents();
    await studentsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await studentsComponentsPage.countDeleteButtons();
    await createStudents();

    await studentsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await studentsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Students', async () => {
    await studentsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await studentsComponentsPage.countDeleteButtons();
    await studentsComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    studentsDeleteDialog = new StudentsDeleteDialog();
    expect(await studentsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.students.delete.question/);
    await studentsDeleteDialog.clickOnConfirmButton();

    await studentsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await studentsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
