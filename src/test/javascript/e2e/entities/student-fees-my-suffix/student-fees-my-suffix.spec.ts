/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import StudentFeesComponentsPage from './student-fees-my-suffix.page-object';
import { StudentFeesDeleteDialog } from './student-fees-my-suffix.page-object';
import StudentFeesUpdatePage from './student-fees-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('StudentFees e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let studentFeesUpdatePage: StudentFeesUpdatePage;
  let studentFeesComponentsPage: StudentFeesComponentsPage;
  let studentFeesDeleteDialog: StudentFeesDeleteDialog;

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

  it('should load StudentFees', async () => {
    await navBarPage.getEntityPage('student-fees-my-suffix');
    studentFeesComponentsPage = new StudentFeesComponentsPage();
    expect(await studentFeesComponentsPage.getTitle().getText()).to.match(/Student Fees/);
  });

  it('should load create StudentFees page', async () => {
    await studentFeesComponentsPage.clickOnCreateButton();
    studentFeesUpdatePage = new StudentFeesUpdatePage();
    expect(await studentFeesUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.studentFees.home.createOrEditLabel/);
    await studentFeesUpdatePage.cancel();
  });

  it('should create and save StudentFees', async () => {
    async function createStudentFees() {
      await studentFeesComponentsPage.clickOnCreateButton();
      await studentFeesUpdatePage.setFeeInput('5');
      expect(await studentFeesUpdatePage.getFeeInput()).to.eq('5');
      await studentFeesUpdatePage.setFeeCorrectionInput('5');
      expect(await studentFeesUpdatePage.getFeeCorrectionInput()).to.eq('5');
      await studentFeesUpdatePage.monthSelectLastOption();
      const selectedFeeStatus = await studentFeesUpdatePage.getFeeStatusInput().isSelected();
      if (selectedFeeStatus) {
        await studentFeesUpdatePage.getFeeStatusInput().click();
        expect(await studentFeesUpdatePage.getFeeStatusInput().isSelected()).to.be.false;
      } else {
        await studentFeesUpdatePage.getFeeStatusInput().click();
        expect(await studentFeesUpdatePage.getFeeStatusInput().isSelected()).to.be.true;
      }
      await studentFeesUpdatePage.setRemarksInput('remarks');
      expect(await studentFeesUpdatePage.getRemarksInput()).to.match(/remarks/);
      await studentFeesUpdatePage.registrationnoSelectLastOption();
      await studentFeesUpdatePage.subjectSelectLastOption();
      await studentFeesUpdatePage.sessionSelectLastOption();
      await studentFeesUpdatePage.teacherSelectLastOption();
      await waitUntilDisplayed(studentFeesUpdatePage.getSaveButton());
      await studentFeesUpdatePage.save();
      await waitUntilHidden(studentFeesUpdatePage.getSaveButton());
      expect(await studentFeesUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createStudentFees();
    await studentFeesComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await studentFeesComponentsPage.countDeleteButtons();
    await createStudentFees();

    await studentFeesComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await studentFeesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last StudentFees', async () => {
    await studentFeesComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await studentFeesComponentsPage.countDeleteButtons();
    await studentFeesComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    studentFeesDeleteDialog = new StudentFeesDeleteDialog();
    expect(await studentFeesDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.studentFees.delete.question/);
    await studentFeesDeleteDialog.clickOnConfirmButton();

    await studentFeesComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await studentFeesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
