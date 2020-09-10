import { element, by, ElementFinder } from 'protractor';

export default class FeaturesUpdatePage {
  pageTitle: ElementFinder = element(by.id('risingArjunApp.features.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  featureIdInput: ElementFinder = element(by.css('input#features-my-suffix-featureId'));
  featureDetailInput: ElementFinder = element(by.css('input#features-my-suffix-featureDetail'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setFeatureIdInput(featureId) {
    await this.featureIdInput.sendKeys(featureId);
  }

  async getFeatureIdInput() {
    return this.featureIdInput.getAttribute('value');
  }

  async setFeatureDetailInput(featureDetail) {
    await this.featureDetailInput.sendKeys(featureDetail);
  }

  async getFeatureDetailInput() {
    return this.featureDetailInput.getAttribute('value');
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
