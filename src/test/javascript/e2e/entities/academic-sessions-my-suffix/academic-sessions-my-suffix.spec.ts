/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import AcademicSessionsComponentsPage from './academic-sessions-my-suffix.page-object';
import { AcademicSessionsDeleteDialog } from './academic-sessions-my-suffix.page-object';
import AcademicSessionsUpdatePage from './academic-sessions-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('AcademicSessions e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let academicSessionsUpdatePage: AcademicSessionsUpdatePage;
  let academicSessionsComponentsPage: AcademicSessionsComponentsPage;
  let academicSessionsDeleteDialog: AcademicSessionsDeleteDialog;

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

  it('should load AcademicSessions', async () => {
    await navBarPage.getEntityPage('academic-sessions-my-suffix');
    academicSessionsComponentsPage = new AcademicSessionsComponentsPage();
    expect(await academicSessionsComponentsPage.getTitle().getText()).to.match(/Academic Sessions/);
  });

  it('should load create AcademicSessions page', async () => {
    await academicSessionsComponentsPage.clickOnCreateButton();
    academicSessionsUpdatePage = new AcademicSessionsUpdatePage();
    expect(await academicSessionsUpdatePage.getPageTitle().getAttribute('id')).to.match(
      /risingArjunApp.academicSessions.home.createOrEditLabel/
    );
    await academicSessionsUpdatePage.cancel();
  });

  it('should create and save AcademicSessions', async () => {
    async function createAcademicSessions() {
      await academicSessionsComponentsPage.clickOnCreateButton();
      await academicSessionsUpdatePage.setAcadSessionIdInput('acadSessionId');
      expect(await academicSessionsUpdatePage.getAcadSessionIdInput()).to.match(/acadSessionId/);
      await academicSessionsUpdatePage.setAcadSessionInput('acadSession');
      expect(await academicSessionsUpdatePage.getAcadSessionInput()).to.match(/acadSession/);
      await waitUntilDisplayed(academicSessionsUpdatePage.getSaveButton());
      await academicSessionsUpdatePage.save();
      await waitUntilHidden(academicSessionsUpdatePage.getSaveButton());
      expect(await academicSessionsUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createAcademicSessions();
    await academicSessionsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await academicSessionsComponentsPage.countDeleteButtons();
    await createAcademicSessions();

    await academicSessionsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await academicSessionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last AcademicSessions', async () => {
    await academicSessionsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await academicSessionsComponentsPage.countDeleteButtons();
    await academicSessionsComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    academicSessionsDeleteDialog = new AcademicSessionsDeleteDialog();
    expect(await academicSessionsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /risingArjunApp.academicSessions.delete.question/
    );
    await academicSessionsDeleteDialog.clickOnConfirmButton();

    await academicSessionsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await academicSessionsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
