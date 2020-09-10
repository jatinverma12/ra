import { element, by, ElementFinder } from 'protractor';

export default class SubjectsUpdatePage {
  pageTitle: ElementFinder = element(by.id('risingArjunApp.subjects.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  subjectCodeInput: ElementFinder = element(by.css('input#subjects-my-suffix-subjectCode'));
  subjectTitleInput: ElementFinder = element(by.css('input#subjects-my-suffix-subjectTitle'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setSubjectCodeInput(subjectCode) {
    await this.subjectCodeInput.sendKeys(subjectCode);
  }

  async getSubjectCodeInput() {
    return this.subjectCodeInput.getAttribute('value');
  }

  async setSubjectTitleInput(subjectTitle) {
    await this.subjectTitleInput.sendKeys(subjectTitle);
  }

  async getSubjectTitleInput() {
    return this.subjectTitleInput.getAttribute('value');
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
