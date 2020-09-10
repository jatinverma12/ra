/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import SalariesPaymentComponentsPage from './salaries-payment-my-suffix.page-object';
import { SalariesPaymentDeleteDialog } from './salaries-payment-my-suffix.page-object';
import SalariesPaymentUpdatePage from './salaries-payment-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('SalariesPayment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let salariesPaymentUpdatePage: SalariesPaymentUpdatePage;
  let salariesPaymentComponentsPage: SalariesPaymentComponentsPage;
  let salariesPaymentDeleteDialog: SalariesPaymentDeleteDialog;

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

  it('should load SalariesPayments', async () => {
    await navBarPage.getEntityPage('salaries-payment-my-suffix');
    salariesPaymentComponentsPage = new SalariesPaymentComponentsPage();
    expect(await salariesPaymentComponentsPage.getTitle().getText()).to.match(/Salaries Payments/);
  });

  it('should load create SalariesPayment page', async () => {
    await salariesPaymentComponentsPage.clickOnCreateButton();
    salariesPaymentUpdatePage = new SalariesPaymentUpdatePage();
    expect(await salariesPaymentUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /risingArjunApp.salariesPayment.home.createOrEditLabel/
    );
    await salariesPaymentUpdatePage.cancel();
  });

  it('should create and save SalariesPayments', async () => {
    async function createSalariesPayment() {
      await salariesPaymentComponentsPage.clickOnCreateButton();
      await salariesPaymentUpdatePage.setSalaryInput('5');
      expect(await salariesPaymentUpdatePage.getSalaryInput()).to.eq('5');
      await salariesPaymentUpdatePage.setPaidInput('5');
      expect(await salariesPaymentUpdatePage.getPaidInput()).to.eq('5');
      await salariesPaymentUpdatePage.setUnpaidInput('5');
      expect(await salariesPaymentUpdatePage.getUnpaidInput()).to.eq('5');
      await salariesPaymentUpdatePage.setDateInput('01-01-2001');
      expect(await salariesPaymentUpdatePage.getDateInput()).to.eq('2001-01-01');
      await salariesPaymentUpdatePage.setTransactionIdInput('transactionId');
      expect(await salariesPaymentUpdatePage.getTransactionIdInput()).to.match(/transactionId/);
      await salariesPaymentUpdatePage.paymentModeSelectLastOption();
      await salariesPaymentUpdatePage.setRemarksInput('remarks');
      expect(await salariesPaymentUpdatePage.getRemarksInput()).to.match(/remarks/);
      await salariesPaymentUpdatePage.employeeSelectLastOption();
      await waitUntilDisplayed(salariesPaymentUpdatePage.getSaveButton());
      await salariesPaymentUpdatePage.save();
      await waitUntilHidden(salariesPaymentUpdatePage.getSaveButton());
      expect(await salariesPaymentUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createSalariesPayment();
    await salariesPaymentComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await salariesPaymentComponentsPage.countDeleteButtons();
    await createSalariesPayment();

    await salariesPaymentComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await salariesPaymentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last SalariesPayment', async () => {
    await salariesPaymentComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await salariesPaymentComponentsPage.countDeleteButtons();
    await salariesPaymentComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    salariesPaymentDeleteDialog = new SalariesPaymentDeleteDialog();
    expect(await salariesPaymentDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /risingArjunApp.salariesPayment.delete.question/
    );
    await salariesPaymentDeleteDialog.clickOnConfirmButton();

    await salariesPaymentComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await salariesPaymentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
