import { element, by, ElementFinder } from 'protractor';

export default class CentersUpdatePage {
  pageTitle: ElementFinder = element(by.id('risingArjunApp.centers.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  centerCodeInput: ElementFinder = element(by.css('input#centers-my-suffix-centerCode'));
  centerTitleInput: ElementFinder = element(by.css('input#centers-my-suffix-centerTitle'));
  streetNoInput: ElementFinder = element(by.css('input#centers-my-suffix-streetNo'));
  citySelect: ElementFinder = element(by.css('select#centers-my-suffix-city'));
  stateSelect: ElementFinder = element(by.css('select#centers-my-suffix-state'));
  pincodeInput: ElementFinder = element(by.css('input#centers-my-suffix-pincode'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setCenterCodeInput(centerCode) {
    await this.centerCodeInput.sendKeys(centerCode);
  }

  async getCenterCodeInput() {
    return this.centerCodeInput.getAttribute('value');
  }

  async setCenterTitleInput(centerTitle) {
    await this.centerTitleInput.sendKeys(centerTitle);
  }

  async getCenterTitleInput() {
    return this.centerTitleInput.getAttribute('value');
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
