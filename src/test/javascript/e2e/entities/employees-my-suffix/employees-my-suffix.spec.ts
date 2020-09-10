/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import EmployeesComponentsPage from './employees-my-suffix.page-object';
import { EmployeesDeleteDialog } from './employees-my-suffix.page-object';
import EmployeesUpdatePage from './employees-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('Employees e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employeesUpdatePage: EmployeesUpdatePage;
  let employeesComponentsPage: EmployeesComponentsPage;
  let employeesDeleteDialog: EmployeesDeleteDialog;
  const fileToUpload = '../../../../../../src/main/webapp/static/images/logo-jhipster.png';
  const absolutePath = path.resolve(__dirname, fileToUpload);

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

  it('should load Employees', async () => {
    await navBarPage.getEntityPage('employees-my-suffix');
    employeesComponentsPage = new EmployeesComponentsPage();
    expect(await employeesComponentsPage.getTitle().getText()).to.match(/Employees/);
  });

  it('should load create Employees page', async () => {
    await employeesComponentsPage.clickOnCreateButton();
    employeesUpdatePage = new EmployeesUpdatePage();
    expect(await employeesUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.employees.home.createOrEditLabel/);
    await employeesUpdatePage.cancel();
  });

  it('should create and save Employees', async () => {
    async function createEmployees() {
      await employeesComponentsPage.clickOnCreateButton();
      await employeesUpdatePage.setEmployeeIdInput('employeeId');
      expect(await employeesUpdatePage.getEmployeeIdInput()).to.match(/employeeId/);
      await employeesUpdatePage.jobNatureSelectLastOption();
      const selectedBgc = await employeesUpdatePage.getBgcInput().isSelected();
      if (selectedBgc) {
        await employeesUpdatePage.getBgcInput().click();
        expect(await employeesUpdatePage.getBgcInput().isSelected()).to.be.false;
      } else {
        await employeesUpdatePage.getBgcInput().click();
        expect(await employeesUpdatePage.getBgcInput().isSelected()).to.be.true;
      }
      await employeesUpdatePage.setResumeInput(absolutePath);
      await employeesUpdatePage.setPanInput('pan');
      expect(await employeesUpdatePage.getPanInput()).to.match(/pan/);
      await employeesUpdatePage.setAccountNoInput('accountNo');
      expect(await employeesUpdatePage.getAccountNoInput()).to.match(/accountNo/);
      await employeesUpdatePage.setBankInput('bank');
      expect(await employeesUpdatePage.getBankInput()).to.match(/bank/);
      await employeesUpdatePage.setIfscInput('ifsc');
      expect(await employeesUpdatePage.getIfscInput()).to.match(/ifsc/);
      await employeesUpdatePage.userSelectLastOption();
      await waitUntilDisplayed(employeesUpdatePage.getSaveButton());
      await employeesUpdatePage.save();
      await waitUntilHidden(employeesUpdatePage.getSaveButton());
      expect(await employeesUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createEmployees();
    await employeesComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await employeesComponentsPage.countDeleteButtons();
    await createEmployees();

    await employeesComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await employeesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Employees', async () => {
    await employeesComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await employeesComponentsPage.countDeleteButtons();
    await employeesComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    employeesDeleteDialog = new EmployeesDeleteDialog();
    expect(await employeesDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.employees.delete.question/);
    await employeesDeleteDialog.clickOnConfirmButton();

    await employeesComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await employeesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
