import { element, by, ElementFinder } from 'protractor';

export default class ScholarshipsUpdatePage {
  pageTitle: ElementFinder = element(by.id('risingArjunApp.scholarships.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  minMarksInput: ElementFinder = element(by.css('input#scholarships-my-suffix-minMarks'));
  percentInput: ElementFinder = element(by.css('input#scholarships-my-suffix-percent'));
  sessionSelect: ElementFinder = element(by.css('select#scholarships-my-suffix-session'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setMinMarksInput(minMarks) {
    await this.minMarksInput.sendKeys(minMarks);
  }

  async getMinMarksInput() {
    return this.minMarksInput.getAttribute('value');
  }

  async setPercentInput(percent) {
    await this.percentInput.sendKeys(percent);
  }

  async getPercentInput() {
    return this.percentInput.getAttribute('value');
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
