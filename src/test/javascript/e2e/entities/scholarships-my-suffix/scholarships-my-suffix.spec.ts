/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ScholarshipsComponentsPage from './scholarships-my-suffix.page-object';
import { ScholarshipsDeleteDialog } from './scholarships-my-suffix.page-object';
import ScholarshipsUpdatePage from './scholarships-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Scholarships e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let scholarshipsUpdatePage: ScholarshipsUpdatePage;
  let scholarshipsComponentsPage: ScholarshipsComponentsPage;
  let scholarshipsDeleteDialog: ScholarshipsDeleteDialog;

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

  it('should load Scholarships', async () => {
    await navBarPage.getEntityPage('scholarships-my-suffix');
    scholarshipsComponentsPage = new ScholarshipsComponentsPage();
    expect(await scholarshipsComponentsPage.getTitle().getText()).to.match(/Scholarships/);
  });

  it('should load create Scholarships page', async () => {
    await scholarshipsComponentsPage.clickOnCreateButton();
    scholarshipsUpdatePage = new ScholarshipsUpdatePage();
    expect(await scholarshipsUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.scholarships.home.createOrEditLabel/);
    await scholarshipsUpdatePage.cancel();
  });

  it('should create and save Scholarships', async () => {
    async function createScholarships() {
      await scholarshipsComponentsPage.clickOnCreateButton();
      await scholarshipsUpdatePage.setMinMarksInput('5');
      expect(await scholarshipsUpdatePage.getMinMarksInput()).to.eq('5');
      await scholarshipsUpdatePage.setPercentInput('5');
      expect(await scholarshipsUpdatePage.getPercentInput()).to.eq('5');
      await scholarshipsUpdatePage.sessionSelectLastOption();
      await waitUntilDisplayed(scholarshipsUpdatePage.getSaveButton());
      await scholarshipsUpdatePage.save();
      await waitUntilHidden(scholarshipsUpdatePage.getSaveButton());
      expect(await scholarshipsUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createScholarships();
    await scholarshipsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await scholarshipsComponentsPage.countDeleteButtons();
    await createScholarships();

    await scholarshipsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await scholarshipsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Scholarships', async () => {
    await scholarshipsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await scholarshipsComponentsPage.countDeleteButtons();
    await scholarshipsComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    scholarshipsDeleteDialog = new ScholarshipsDeleteDialog();
    expect(await scholarshipsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.scholarships.delete.question/);
    await scholarshipsDeleteDialog.clickOnConfirmButton();

    await scholarshipsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await scholarshipsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
