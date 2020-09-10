import { element, by, ElementFinder } from 'protractor';

export default class UserDetailsUpdatePage {
  pageTitle: ElementFinder = element(by.id('risingArjunApp.userDetails.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  mobileNoInput: ElementFinder = element(by.css('input#user-details-my-suffix-mobileNo'));
  dobInput: ElementFinder = element(by.css('input#user-details-my-suffix-dob'));
  houseNoInput: ElementFinder = element(by.css('input#user-details-my-suffix-houseNo'));
  streetNoInput: ElementFinder = element(by.css('input#user-details-my-suffix-streetNo'));
  citySelect: ElementFinder = element(by.css('select#user-details-my-suffix-city'));
  stateSelect: ElementFinder = element(by.css('select#user-details-my-suffix-state'));
  pincodeInput: ElementFinder = element(by.css('input#user-details-my-suffix-pincode'));
  userSelect: ElementFinder = element(by.css('select#user-details-my-suffix-user'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setMobileNoInput(mobileNo) {
    await this.mobileNoInput.sendKeys(mobileNo);
  }

  async getMobileNoInput() {
    return this.mobileNoInput.getAttribute('value');
  }

  async setDobInput(dob) {
    await this.dobInput.sendKeys(dob);
  }

  async getDobInput() {
    return this.dobInput.getAttribute('value');
  }

  async setHouseNoInput(houseNo) {
    await this.houseNoInput.sendKeys(houseNo);
  }

  async getHouseNoInput() {
    return this.houseNoInput.getAttribute('value');
  }

  async setStreetNoInput(streetNo) {
    await this.streetNoInput.sendKeys(streetNo);
  }

  async getStreetNoInput() {
    return this.streetNoInput.getAttribute('value');
  }

  async setCitySelect(city) {
    await this.citySelect.sendKeys(city);
  }

  async getCitySelect() {
    return this.citySelect.element(by.css('option:checked')).getText();
  }

  async citySelectLastOption() {
    await this.citySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  async setStateSelect(state) {
    await this.stateSelect.sendKeys(state);
  }

  async getStateSelect() {
    return this.stateSelect.element(by.css('option:checked')).getText();
  }

  async stateSelectLastOption() {
    await this.stateSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }
  async setPincodeInput(pincode) {
    await this.pincodeInput.sendKeys(pincode);
  }

  async getPincodeInput() {
    return this.pincodeInput.getAttribute('value');
  }

  async userSelectLastOption() {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option) {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect() {
    return this.userSelect;
  }

  async getUserSelectedOption() {
    return this.userSelect.element(by.css('option:checked')).getText();
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
