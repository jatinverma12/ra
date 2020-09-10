import { element, by, ElementFinder } from 'protractor';

export default class DiscountsUpdatePage {
  pageTitle: ElementFinder = element(by.id('risingArjunApp.discounts.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  subject2Input: ElementFinder = element(by.css('input#discounts-my-suffix-subject2'));
  subject3Input: ElementFinder = element(by.css('input#discounts-my-suffix-subject3'));
  subject4Input: ElementFinder = element(by.css('input#discounts-my-suffix-subject4'));
  subject5Input: ElementFinder = element(by.css('input#discounts-my-suffix-subject5'));
  subject6Input: ElementFinder = element(by.css('input#discounts-my-suffix-subject6'));
  subject7Input: ElementFinder = element(by.css('input#discounts-my-suffix-subject7'));
  subject8Input: ElementFinder = element(by.css('input#discounts-my-suffix-subject8'));
  quarterlyInput: ElementFinder = element(by.css('input#discounts-my-suffix-quarterly'));
  halfYearlyInput: ElementFinder = element(by.css('input#discounts-my-suffix-halfYearly'));
  annuallyInput: ElementFinder = element(by.css('input#discounts-my-suffix-annually'));
  siblingInput: ElementFinder = element(by.css('input#discounts-my-suffix-sibling'));
  referralInput: ElementFinder = element(by.css('input#discounts-my-suffix-referral'));
  sessionSelect: ElementFinder = element(by.css('select#discounts-my-suffix-session'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setSubject2Input(subject2) {
    await this.subject2Input.sendKeys(subject2);
  }

  async getSubject2Input() {
    return this.subject2Input.getAttribute('value');
  }

  async setSubject3Input(subject3) {
    await this.subject3Input.sendKeys(subject3);
  }

  async getSubject3Input() {
    return this.subject3Input.getAttribute('value');
  }

  async setSubject4Input(subject4) {
    await this.subject4Input.sendKeys(subject4);
  }

  async getSubject4Input() {
    return this.subject4Input.getAttribute('value');
  }

  async setSubject5Input(subject5) {
    await this.subject5Input.sendKeys(subject5);
  }

  async getSubject5Input() {
    return this.subject5Input.getAttribute('value');
  }

  async setSubject6Input(subject6) {
    await this.subject6Input.sendKeys(subject6);
  }

  async getSubject6Input() {
    return this.subject6Input.getAttribute('value');
  }

  async setSubject7Input(subject7) {
    await this.subject7Input.sendKeys(subject7);
  }

  async getSubject7Input() {
    return this.subject7Input.getAttribute('value');
  }

  async setSubject8Input(subject8) {
    await this.subject8Input.sendKeys(subject8);
  }

  async getSubject8Input() {
    return this.subject8Input.getAttribute('value');
  }

  async setQuarterlyInput(quarterly) {
    await this.quarterlyInput.sendKeys(quarterly);
  }

  async getQuarterlyInput() {
    return this.quarterlyInput.getAttribute('value');
  }

  async setHalfYearlyInput(halfYearly) {
    await this.halfYearlyInput.sendKeys(halfYearly);
  }

  async getHalfYearlyInput() {
    return this.halfYearlyInput.getAttribute('value');
  }

  async setAnnuallyInput(annually) {
    await this.annuallyInput.sendKeys(annually);
  }

  async getAnnuallyInput() {
    return this.annuallyInput.getAttribute('value');
  }

  async setSiblingInput(sibling) {
    await this.siblingInput.sendKeys(sibling);
  }

  async getSiblingInput() {
    return this.siblingInput.getAttribute('value');
  }

  async setReferralInput(referral) {
    await this.referralInput.sendKeys(referral);
  }

  async getReferralInput() {
    return this.referralInput.getAttribute('value');
  }

  async sessionSelectLastOption() {
    await this.sessionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async sessionSelectOption(option) {
    await this.sessionSelect.sendKeys(option);
  }

  getSessionSelect() {
    return this.sessionSelect;
  }

  async getSessionSelectedOption() {
    return this.sessionSelect.element(by.css('option:checked')).getText();
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
