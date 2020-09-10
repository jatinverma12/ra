import { element, by, ElementFinder } from 'protractor';

export default class SalariesPaymentUpdatePage {
  pageTitle: ElementFinder = element(by.id('risingArjunApp.salariesPayment.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  salaryInput: ElementFinder = element(by.css('input#salaries-payment-my-suffix-salary'));
  paidInput: ElementFinder = element(by.css('input#salaries-payment-my-suffix-paid'));
  unpaidInput: ElementFinder = element(by.css('input#salaries-payment-my-suffix-unpaid'));
  dateInput: ElementFinder = element(by.css('input#salaries-payment-my-suffix-date'));
  transactionIdInput: ElementFinder = element(by.css('input#salaries-payment-my-suffix-transactionId'));
  paymentModeSelect: ElementFinder = element(by.css('select#salaries-payment-my-suffix-paymentMode'));
  remarksInput: ElementFinder = element(by.css('input#salaries-payment-my-suffix-remarks'));
  employeeSelect: ElementFinder = element(by.css('select#salaries-payment-my-suffix-employee'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setSalaryInput(salary) {
    await this.salaryInput.sendKeys(salary);
  }

  async getSalaryInput() {
    return this.salaryInput.getAttribute('value');
  }

  async setPaidInput(paid) {
    await this.paidInput.sendKeys(paid);
  }

  async getPaidInput() {
    return this.paidInput.getAttribute('value');
  }

  async setUnpaidInput(unpaid) {
    await this.unpaidInput.sendKeys(unpaid);
  }

  async getUnpaidInput() {
    return this.unpaidInput.getAttribute('value');
  }

  async setDateInput(date) {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput() {
    return this.dateInput.getAttribute('value');
  }

  async setTransactionIdInput(transactionId) {
    await this.transactionIdInput.sendKeys(transactionId);
  }

  async getTransactionIdInput() {
    return this.transactionIdInput.getAttribute('value');
  }

  async setPaymentModeSelect(paymentMode) {
    await this.paymentModeSelect.sendKeys(paymentMode);
  }

  async getPaymentModeSelect() {
    return this.paymentModeSelect.element(by.css('option:checked')).getText();
  }

  async paymentModeSelectLastOption() {
    await this.paymentModeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  async setRemarksInput(remarks) {
    await this.remarksInput.sendKeys(remarks);
  }

  async getRemarksInput() {
    return this.remarksInput.getAttribute('value');
  }

  async employeeSelectLastOption() {
    await this.employeeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async employeeSelectOption(option) {
    await this.employeeSelect.sendKeys(option);
  }

  getEmployeeSelect() {
    return this.employeeSelect;
  }

  async getEmployeeSelectedOption() {
    return this.employeeSelect.element(by.css('option:checked')).getText();
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }
}
