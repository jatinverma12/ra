/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import StudentScoreComponentsPage from './student-score-my-suffix.page-object';
import { StudentScoreDeleteDialog } from './student-score-my-suffix.page-object';
import StudentScoreUpdatePage from './student-score-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('StudentScore e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let studentScoreUpdatePage: StudentScoreUpdatePage;
  let studentScoreComponentsPage: StudentScoreComponentsPage;
  let studentScoreDeleteDialog: StudentScoreDeleteDialog;

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

  it('should load StudentScores', async () => {
    await navBarPage.getEntityPage('student-score-my-suffix');
    studentScoreComponentsPage = new StudentScoreComponentsPage();
    expect(await studentScoreComponentsPage.getTitle().getText()).to.match(/Student Scores/);
  });

  it('should load create StudentScore page', async () => {
    await studentScoreComponentsPage.clickOnCreateButton();
    studentScoreUpdatePage = new StudentScoreUpdatePage();
    expect(await studentScoreUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.studentScore.home.createOrEditLabel/);
    await studentScoreUpdatePage.cancel();
  });

  it('should create and save StudentScores', async () => {
    async function createStudentScore() {
      await studentScoreComponentsPage.clickOnCreateButton();
      await studentScoreUpdatePage.setAnswerInput('answer');
      expect(await studentScoreUpdatePage.getAnswerInput()).to.match(/answer/);
      await studentScoreUpdatePage.setScoreInput('5');
      expect(await studentScoreUpdatePage.getScoreInput()).to.eq('5');
      await studentScoreUpdatePage.setDateInput('01-01-2001');
      expect(await studentScoreUpdatePage.getDateInput()).to.eq('2001-01-01');
      await studentScoreUpdatePage.studentSelectLastOption();
      await studentScoreUpdatePage.questionIdSelectLastOption();
      await waitUntilDisplayed(studentScoreUpdatePage.getSaveButton());
      await studentScoreUpdatePage.save();
      await waitUntilHidden(studentScoreUpdatePage.getSaveButton());
      expect(await studentScoreUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createStudentScore();
    await studentScoreComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await studentScoreComponentsPage.countDeleteButtons();
    await createStudentScore();

    await studentScoreComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await studentScoreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last StudentScore', async () => {
    await studentScoreComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await studentScoreComponentsPage.countDeleteButtons();
    await studentScoreComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    studentScoreDeleteDialog = new StudentScoreDeleteDialog();
    expect(await studentScoreDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.studentScore.delete.question/);
    await studentScoreDeleteDialog.clickOnConfirmButton();

    await studentScoreComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await studentScoreComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
