/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CoursesComponentsPage from './courses-my-suffix.page-object';
import { CoursesDeleteDialog } from './courses-my-suffix.page-object';
import CoursesUpdatePage from './courses-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Courses e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let coursesUpdatePage: CoursesUpdatePage;
  let coursesComponentsPage: CoursesComponentsPage;
  let coursesDeleteDialog: CoursesDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
  });

  it('should load Courses', async () => {
    await navBarPage.getEntityPage('courses-my-suffix');
    coursesComponentsPage = new CoursesComponentsPage();
    expect(await coursesComponentsPage.getTitle().getText()).to.match(/Courses/);
  });

  it('should load create Courses page', async () => {
    await coursesComponentsPage.clickOnCreateButton();
    coursesUpdatePage = new CoursesUpdatePage();
    expect(await coursesUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.courses.home.createOrEditLabel/);
    await coursesUpdatePage.cancel();
  });

  it('should create and save Courses', async () => {
    async function createCourses() {
      await coursesComponentsPage.clickOnCreateButton();
      await coursesUpdatePage.setCourseIdInput('courseId');
      expect(await coursesUpdatePage.getCourseIdInput()).to.match(/courseId/);
      await coursesUpdatePage.setCourseInput('course');
      expect(await coursesUpdatePage.getCourseInput()).to.match(/course/);
      await waitUntilDisplayed(coursesUpdatePage.getSaveButton());
      await coursesUpdatePage.save();
      await waitUntilHidden(coursesUpdatePage.getSaveButton());
      expect(await coursesUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createCourses();
    await coursesComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await coursesComponentsPage.countDeleteButtons();
    await createCourses();

    await coursesComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await coursesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Courses', async () => {
    await coursesComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await coursesComponentsPage.countDeleteButtons();
    await coursesComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    coursesDeleteDialog = new CoursesDeleteDialog();
    expect(await coursesDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.courses.delete.question/);
    await coursesDeleteDialog.clickOnConfirmButton();

    await coursesComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await coursesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
