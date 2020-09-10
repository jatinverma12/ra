/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import SubjectsBaseFeeComponentsPage from './subjects-base-fee-my-suffix.page-object';
import { SubjectsBaseFeeDeleteDialog } from './subjects-base-fee-my-suffix.page-object';
import SubjectsBaseFeeUpdatePage from './subjects-base-fee-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('SubjectsBaseFee e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let subjectsBaseFeeUpdatePage: SubjectsBaseFeeUpdatePage;
  let subjectsBaseFeeComponentsPage: SubjectsBaseFeeComponentsPage;
  let subjectsBaseFeeDeleteDialog: SubjectsBaseFeeDeleteDialog;

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

  it('should load SubjectsBaseFees', async () => {
    await navBarPage.getEntityPage('subjects-base-fee-my-suffix');
    subjectsBaseFeeComponentsPage = new SubjectsBaseFeeComponentsPage();
    expect(await subjectsBaseFeeComponentsPage.getTitle().getText()).to.match(/Subjects Base Fees/);
  });

  it('should load create SubjectsBaseFee page', async () => {
    await subjectsBaseFeeComponentsPage.clickOnCreateButton();
    subjectsBaseFeeUpdatePage = new SubjectsBaseFeeUpdatePage();
    expect(await subjectsBaseFeeUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /risingArjunApp.subjectsBaseFee.home.createOrEditLabel/
    );
    await subjectsBaseFeeUpdatePage.cancel();
  });

  it('should create and save SubjectsBaseFees', async () => {
    async function createSubjectsBaseFee() {
      await subjectsBaseFeeComponentsPage.clickOnCreateButton();
      await subjectsBaseFeeUpdatePage.setBaseFeeInput('5');
      expect(await subjectsBaseFeeUpdatePage.getBaseFeeInput()).to.eq('5');
      await subjectsBaseFeeUpdatePage.courseSelectLastOption();
      await subjectsBaseFeeUpdatePage.sessionSelectLastOption();
      await waitUntilDisplayed(subjectsBaseFeeUpdatePage.getSaveButton());
      await subjectsBaseFeeUpdatePage.save();
      await waitUntilHidden(subjectsBaseFeeUpdatePage.getSaveButton());
      expect(await subjectsBaseFeeUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createSubjectsBaseFee();
    await subjectsBaseFeeComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await subjectsBaseFeeComponentsPage.countDeleteButtons();
    await createSubjectsBaseFee();

    await subjectsBaseFeeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await subjectsBaseFeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last SubjectsBaseFee', async () => {
    await subjectsBaseFeeComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await subjectsBaseFeeComponentsPage.countDeleteButtons();
    await subjectsBaseFeeComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    subjectsBaseFeeDeleteDialog = new SubjectsBaseFeeDeleteDialog();
    expect(await subjectsBaseFeeDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /risingArjunApp.subjectsBaseFee.delete.question/
    );
    await subjectsBaseFeeDeleteDialog.clickOnConfirmButton();

    await subjectsBaseFeeComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await subjectsBaseFeeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
