import { element, by, ElementFinder } from 'protractor';

export default class QuestionsUpdatePage {
  pageTitle: ElementFinder = element(by.id('risingArjunApp.questions.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  questionInput: ElementFinder = element(by.css('textarea#questions-my-suffix-question'));
  diagramInput: ElementFinder = element(by.css('input#file_diagram'));
  option1Input: ElementFinder = element(by.css('input#questions-my-suffix-option1'));
  option2Input: ElementFinder = element(by.css('input#questions-my-suffix-option2'));
  option3Input: ElementFinder = element(by.css('input#questions-my-suffix-option3'));
  option4Input: ElementFinder = element(by.css('input#questions-my-suffix-option4'));
  answerInput: ElementFinder = element(by.css('input#questions-my-suffix-answer'));
  maxMarksInput: ElementFinder = element(by.css('input#questions-my-suffix-maxMarks'));
  negativeMarksInput: ElementFinder = element(by.css('input#questions-my-suffix-negativeMarks'));
  levelSelect: ElementFinder = element(by.css('select#questions-my-suffix-level'));
  courseSelect: ElementFinder = element(by.css('select#questions-my-suffix-course'));
  subjectSelect: ElementFinder = element(by.css('select#questions-my-suffix-subject'));
  chapterSelect: ElementFinder = element(by.css('select#questions-my-suffix-chapter'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setQuestionInput(question) {
    await this.questionInput.sendKeys(question);
  }

  async getQuestionInput() {
    return this.questionInput.getAttribute('value');
  }

  async setDiagramInput(diagram) {
    await this.diagramInput.sendKeys(diagram);
  }

  async getDiagramInput() {
    return this.diagramInput.getAttribute('value');
  }

  async setOption1Input(option1) {
    await this.option1Input.sendKeys(option1);
  }

  async getOption1Input() {
    return this.option1Input.getAttribute('value');
  }

  async setOption2Input(option2) {
    await this.option2Input.sendKeys(option2);
  }

  async getOption2Input() {
    return this.option2Input.getAttribute('value');
  }

  async setOption3Input(option3) {
    await this.option3Input.sendKeys(option3);
  }

  async getOption3Input() {
    return this.option3Input.getAttribute('value');
  }

  async setOption4Input(option4) {
    await this.option4Input.sendKeys(option4);
  }

  async getOption4Input() {
    return this.option4Input.getAttribute('value');
  }

  async setAnswerInput(answer) {
    await this.answerInput.sendKeys(answer);
  }

  async getAnswerInput() {
    return this.answerInput.getAttribute('value');
  }

  async setMaxMarksInput(maxMarks) {
    await this.maxMarksInput.sendKeys(maxMarks);
  }

  async getMaxMarksInput() {
    return this.maxMarksInput.getAttribute('value');
  }

  async setNegativeMarksInput(negativeMarks) {
    await this.negativeMarksInput.sendKeys(negativeMarks);
  }

  async getNegativeMarksInput() {
    return this.negativeMarksInput.getAttribute('value');
  }

  async setLevelSelect(level) {
    await this.levelSelect.sendKeys(level);
  }

  async getLevelSelect() {
    return this.levelSelect.element(by.css('option:checked')).getText();
  }

  async levelSelectLastOption() {
    await this.levelSelect
      .all(by.tagName('option'))
      .last()
      .click();
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

  async chapterSelectLastOption() {
    await this.chapterSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async chapterSelectOption(option) {
    await this.chapterSelect.sendKeys(option);
  }

  getChapterSelect() {
    return this.chapterSelect;
  }

  async getChapterSelectedOption() {
    return this.chapterSelect.element(by.css('option:checked')).getText();
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
