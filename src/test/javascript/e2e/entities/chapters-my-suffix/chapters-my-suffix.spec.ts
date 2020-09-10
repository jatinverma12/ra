/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ChaptersComponentsPage from './chapters-my-suffix.page-object';
import { ChaptersDeleteDialog } from './chapters-my-suffix.page-object';
import ChaptersUpdatePage from './chapters-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Chapters e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let chaptersUpdatePage: ChaptersUpdatePage;
  let chaptersComponentsPage: ChaptersComponentsPage;
  let chaptersDeleteDialog: ChaptersDeleteDialog;

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

  it('should load Chapters', async () => {
    await navBarPage.getEntityPage('chapters-my-suffix');
    chaptersComponentsPage = new ChaptersComponentsPage();
    expect(await chaptersComponentsPage.getTitle().getText()).to.match(/Chapters/);
  });

  it('should load create Chapters page', async () => {
    await chaptersComponentsPage.clickOnCreateButton();
    chaptersUpdatePage = new ChaptersUpdatePage();
    expect(await chaptersUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.chapters.home.createOrEditLabel/);
    await chaptersUpdatePage.cancel();
  });

  it('should create and save Chapters', async () => {
    async function createChapters() {
      await chaptersComponentsPage.clickOnCreateButton();
      await chaptersUpdatePage.setChapterIdInput('chapterId');
      expect(await chaptersUpdatePage.getChapterIdInput()).to.match(/chapterId/);
      await chaptersUpdatePage.setChapterTitleInput('chapterTitle');
      expect(await chaptersUpdatePage.getChapterTitleInput()).to.match(/chapterTitle/);
      await chaptersUpdatePage.courseSelectLastOption();
      await chaptersUpdatePage.subjectSelectLastOption();
      await waitUntilDisplayed(chaptersUpdatePage.getSaveButton());
      await chaptersUpdatePage.save();
      await waitUntilHidden(chaptersUpdatePage.getSaveButton());
      expect(await chaptersUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createChapters();
    await chaptersComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await chaptersComponentsPage.countDeleteButtons();
    await createChapters();

    await chaptersComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await chaptersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Chapters', async () => {
    await chaptersComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await chaptersComponentsPage.countDeleteButtons();
    await chaptersComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    chaptersDeleteDialog = new ChaptersDeleteDialog();
    expect(await chaptersDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.chapters.delete.question/);
    await chaptersDeleteDialog.clickOnConfirmButton();

    await chaptersComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await chaptersComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
