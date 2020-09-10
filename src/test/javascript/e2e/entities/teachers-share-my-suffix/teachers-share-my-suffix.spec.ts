/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TeachersShareComponentsPage from './teachers-share-my-suffix.page-object';
import { TeachersShareDeleteDialog } from './teachers-share-my-suffix.page-object';
import TeachersShareUpdatePage from './teachers-share-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('TeachersShare e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let teachersShareUpdatePage: TeachersShareUpdatePage;
  let teachersShareComponentsPage: TeachersShareComponentsPage;
  let teachersShareDeleteDialog: TeachersShareDeleteDialog;

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

  it('should load TeachersShares', async () => {
    await navBarPage.getEntityPage('teachers-share-my-suffix');
    teachersShareComponentsPage = new TeachersShareComponentsPage();
    expect(await teachersShareComponentsPage.getTitle().getText()).to.match(/Teachers Shares/);
  });

  it('should load create TeachersShare page', async () => {
    await teachersShareComponentsPage.clickOnCreateButton();
    teachersShareUpdatePage = new TeachersShareUpdatePage();
    expect(await teachersShareUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.teachersShare.home.createOrEditLabel/);
    await teachersShareUpdatePage.cancel();
  });

  it('should create and save TeachersShares', async () => {
    async function createTeachersShare() {
      await teachersShareComponentsPage.clickOnCreateButton();
      await teachersShareUpdatePage.setShareInput('5');
      expect(await teachersShareUpdatePage.getShareInput()).to.eq('5');
      await teachersShareUpdatePage.setPlannedClassesInput('5');
      expect(await teachersShareUpdatePage.getPlannedClassesInput()).to.eq('5');
      await teachersShareUpdatePage.setActualClassesInput('5');
      expect(await teachersShareUpdatePage.getActualClassesInput()).to.eq('5');
      await teachersShareUpdatePage.setShareCorrectionInput('5');
      expect(await teachersShareUpdatePage.getShareCorrectionInput()).to.eq('5');
      await teachersShareUpdatePage.monthSelectLastOption();
      await teachersShareUpdatePage.setRemarksInput('remarks');
      expect(await teachersShareUpdatePage.getRemarksInput()).to.match(/remarks/);
      await teachersShareUpdatePage.teacherSelectLastOption();
      await teachersShareUpdatePage.subjectSelectLastOption();
      await teachersShareUpdatePage.courseSelectLastOption();
      await teachersShareUpdatePage.sessionSelectLastOption();
      await waitUntilDisplayed(teachersShareUpdatePage.getSaveButton());
      await teachersShareUpdatePage.save();
      await waitUntilHidden(teachersShareUpdatePage.getSaveButton());
      expect(await teachersShareUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createTeachersShare();
    await teachersShareComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await teachersShareComponentsPage.countDeleteButtons();
    await createTeachersShare();

    await teachersShareComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await teachersShareComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last TeachersShare', async () => {
    await teachersShareComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await teachersShareComponentsPage.countDeleteButtons();
    await teachersShareComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    teachersShareDeleteDialog = new TeachersShareDeleteDialog();
    expect(await teachersShareDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.teachersShare.delete.question/);
    await teachersShareDeleteDialog.clickOnConfirmButton();

    await teachersShareComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await teachersShareComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
