import { element, by, ElementFinder } from 'protractor';

export default class TeachersUpdatePage {
  pageTitle: ElementFinder = element(by.id('risingArjunApp.teachers.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  teacherSelect: ElementFinder = element(by.css('select#teachers-my-suffix-teacher'));
  subjectsSelect: ElementFinder = element(by.css('select#teachers-my-suffix-subjects'));
  coursesSelect: ElementFinder = element(by.css('select#teachers-my-suffix-courses'));

  getPageTitle() {
    return this.pageTitle;
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

  async subjectsSelectLastOption() {
    await this.subjectsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async subjectsSelectOption(option) {
    await this.subjectsSelect.sendKeys(option);
  }

  getSubjectsSelect() {
    return this.subjectsSelect;
  }

  async getSubjectsSelectedOption() {
    return this.subjectsSelect.element(by.css('option:checked')).getText();
  }

  async coursesSelectLastOption() {
    await this.coursesSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async coursesSelectOption(option) {
    await this.coursesSelect.sendKeys(option);
  }

  getCoursesSelect() {
    return this.coursesSelect;
  }

  async getCoursesSelectedOption() {
    return this.coursesSelect.element(by.css('option:checked')).getText();
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
