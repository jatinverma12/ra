/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import QuestionsComponentsPage from './questions-my-suffix.page-object';
import { QuestionsDeleteDialog } from './questions-my-suffix.page-object';
import QuestionsUpdatePage from './questions-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('Questions e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let questionsUpdatePage: QuestionsUpdatePage;
  let questionsComponentsPage: QuestionsComponentsPage;
  let questionsDeleteDialog: QuestionsDeleteDialog;
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

  it('should load Questions', async () => {
    await navBarPage.getEntityPage('questions-my-suffix');
    questionsComponentsPage = new QuestionsComponentsPage();
    expect(await questionsComponentsPage.getTitle().getText()).to.match(/Questions/);
  });

  it('should load create Questions page', async () => {
    await questionsComponentsPage.clickOnCreateButton();
    questionsUpdatePage = new QuestionsUpdatePage();
    expect(await questionsUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.questions.home.createOrEditLabel/);
    await questionsUpdatePage.cancel();
  });

  it('should create and save Questions', async () => {
    async function createQuestions() {
      await questionsComponentsPage.clickOnCreateButton();
      await questionsUpdatePage.setQuestionInput('question');
      expect(await questionsUpdatePage.getQuestionInput()).to.match(/question/);
      await questionsUpdatePage.setDiagramInput(absolutePath);
      await questionsUpdatePage.setOption1Input('option1');
      expect(await questionsUpdatePage.getOption1Input()).to.match(/option1/);
      await questionsUpdatePage.setOption2Input('option2');
      expect(await questionsUpdatePage.getOption2Input()).to.match(/option2/);
      await questionsUpdatePage.setOption3Input('option3');
      expect(await questionsUpdatePage.getOption3Input()).to.match(/option3/);
      await questionsUpdatePage.setOption4Input('option4');
      expect(await questionsUpdatePage.getOption4Input()).to.match(/option4/);
      await questionsUpdatePage.setAnswerInput('answer');
      expect(await questionsUpdatePage.getAnswerInput()).to.match(/answer/);
      await questionsUpdatePage.setMaxMarksInput('5');
      expect(await questionsUpdatePage.getMaxMarksInput()).to.eq('5');
      await questionsUpdatePage.setNegativeMarksInput('5');
      expect(await questionsUpdatePage.getNegativeMarksInput()).to.eq('5');
      await questionsUpdatePage.levelSelectLastOption();
      await questionsUpdatePage.courseSelectLastOption();
      await questionsUpdatePage.subjectSelectLastOption();
      await questionsUpdatePage.chapterSelectLastOption();
      await waitUntilDisplayed(questionsUpdatePage.getSaveButton());
      await questionsUpdatePage.save();
      await waitUntilHidden(questionsUpdatePage.getSaveButton());
      expect(await questionsUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createQuestions();
    await questionsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await questionsComponentsPage.countDeleteButtons();
    await createQuestions();

    await questionsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await questionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Questions', async () => {
    await questionsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await questionsComponentsPage.countDeleteButtons();
    await questionsComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    questionsDeleteDialog = new QuestionsDeleteDialog();
    expect(await questionsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.questions.delete.question/);
    await questionsDeleteDialog.clickOnConfirmButton();

    await questionsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await questionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
