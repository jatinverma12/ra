import { element, by, ElementFinder } from 'protractor';

export default class TeachersShareUpdatePage {
  pageTitle: ElementFinder = element(by.id('risingArjunApp.teachersShare.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  shareInput: ElementFinder = element(by.css('input#teachers-share-my-suffix-share'));
  plannedClassesInput: ElementFinder = element(by.css('input#teachers-share-my-suffix-plannedClasses'));
  actualClassesInput: ElementFinder = element(by.css('input#teachers-share-my-suffix-actualClasses'));
  shareCorrectionInput: ElementFinder = element(by.css('input#teachers-share-my-suffix-shareCorrection'));
  monthSelect: ElementFinder = element(by.css('select#teachers-share-my-suffix-month'));
  remarksInput: ElementFinder = element(by.css('input#teachers-share-my-suffix-remarks'));
  teacherSelect: ElementFinder = element(by.css('select#teachers-share-my-suffix-teacher'));
  subjectSelect: ElementFinder = element(by.css('select#teachers-share-my-suffix-subject'));
  courseSelect: ElementFinder = element(by.css('select#teachers-share-my-suffix-course'));
  sessionSelect: ElementFinder = element(by.css('select#teachers-share-my-suffix-session'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setShareInput(share) {
    await this.shareInput.sendKeys(share);
  }

  async getShareInput() {
    return this.shareInput.getAttribute('value');
  }

  async setPlannedClassesInput(plannedClasses) {
    await this.plannedClassesInput.sendKeys(plannedClasses);
  }

  async getPlannedClassesInput() {
    return this.plannedClassesInput.getAttribute('value');
  }

  async setActualClassesInput(actualClasses) {
    await this.actualClassesInput.sendKeys(actualClasses);
  }

  async getActualClassesInput() {
    return this.actualClassesInput.getAttribute('value');
  }

  async setShareCorrectionInput(shareCorrection) {
    await this.shareCorrectionInput.sendKeys(shareCorrection);
  }

  async getShareCorrectionInput() {
    return this.shareCorrectionInput.getAttribute('value');
  }

  async setMonthSelect(month) {
    await this.monthSelect.sendKeys(month);
  }

  async getMonthSelect() {
    return this.monthSelect.element(by.css('option:checked')).getText();
  }

  async monthSelectLastOption() {
    await this.monthSelect
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

  async teacherSelectLastOption() {
    await this.teacherSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async teacherSelectOption(option) {
    await this.teacherSelect.sendKeys(option);
  }

  getTeacherSelect() {
    return this.teacherSelect;
  }

  async getTeacherSelectedOption() {
    return this.teacherSelect.element(by.css('option:checked')).getText();
  }

  async subjectSelectLastOption() {
    await this.subjectSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async subjectSelectOption(option) {
    await this.subjectSelect.sendKeys(option);
  }

  getSubjectSelect() {
    return this.subjectSelect;
  }

  async getSubjectSelectedOption() {
    return this.subjectSelect.element(by.css('option:checked')).getText();
  }

  async courseSelectLastOption() {
    await this.courseSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async courseSelectOption(option) {
    await this.courseSelect.sendKeys(option);
  }

  getCourseSelect() {
    return this.courseSelect;
  }

  async getCourseSelectedOption() {
    return this.courseSelect.element(by.css('option:checked')).getText();
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
