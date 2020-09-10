/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ExpensesComponentsPage from './expenses-my-suffix.page-object';
import { ExpensesDeleteDialog } from './expenses-my-suffix.page-object';
import ExpensesUpdatePage from './expenses-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('Expenses e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let expensesUpdatePage: ExpensesUpdatePage;
  let expensesComponentsPage: ExpensesComponentsPage;
  let expensesDeleteDialog: ExpensesDeleteDialog;
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

  it('should load Expenses', async () => {
    await navBarPage.getEntityPage('expenses-my-suffix');
    expensesComponentsPage = new ExpensesComponentsPage();
    expect(await expensesComponentsPage.getTitle().getText()).to.match(/Expenses/);
  });

  it('should load create Expenses page', async () => {
    await expensesComponentsPage.clickOnCreateButton();
    expensesUpdatePage = new ExpensesUpdatePage();
    expect(await expensesUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.expenses.home.createOrEditLabel/);
    await expensesUpdatePage.cancel();
  });

  it('should create and save Expenses', async () => {
    async function createExpenses() {
      await expensesComponentsPage.clickOnCreateButton();
      await expensesUpdatePage.setItemInput('item');
      expect(await expensesUpdatePage.getItemInput()).to.match(/item/);
      await expensesUpdatePage.setQuantityInput('5');
      expect(await expensesUpdatePage.getQuantityInput()).to.eq('5');
      await expensesUpdatePage.setRateInput('5');
      expect(await expensesUpdatePage.getRateInput()).to.eq('5');
      await expensesUpdatePage.setLaborCostInput('5');
      expect(await expensesUpdatePage.getLaborCostInput()).to.eq('5');
      await expensesUpdatePage.setOtherExpenseInput('5');
      expect(await expensesUpdatePage.getOtherExpenseInput()).to.eq('5');
      await expensesUpdatePage.setTotalInput('5');
      expect(await expensesUpdatePage.getTotalInput()).to.eq('5');
      await expensesUpdatePage.setDateInput('01-01-2001');
      expect(await expensesUpdatePage.getDateInput()).to.eq('2001-01-01');
      await expensesUpdatePage.setTransactionIdInput('transactionId');
      expect(await expensesUpdatePage.getTransactionIdInput()).to.match(/transactionId/);
      await expensesUpdatePage.expenseModeSelectLastOption();
      await expensesUpdatePage.typeSelectLastOption();
      await expensesUpdatePage.setBillInput(absolutePath);
      await expensesUpdatePage.setRemarksInput('remarks');
      expect(await expensesUpdatePage.getRemarksInput()).to.match(/remarks/);
      await expensesUpdatePage.incurredBySelectLastOption();
      await waitUntilDisplayed(expensesUpdatePage.getSaveButton());
      await expensesUpdatePage.save();
      await waitUntilHidden(expensesUpdatePage.getSaveButton());
      expect(await expensesUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createExpenses();
    await expensesComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await expensesComponentsPage.countDeleteButtons();
    await createExpenses();

    await expensesComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await expensesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Expenses', async () => {
    await expensesComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await expensesComponentsPage.countDeleteButtons();
    await expensesComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    expensesDeleteDialog = new ExpensesDeleteDialog();
    expect(await expensesDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.expenses.delete.question/);
    await expensesDeleteDialog.clickOnConfirmButton();

    await expensesComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await expensesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
