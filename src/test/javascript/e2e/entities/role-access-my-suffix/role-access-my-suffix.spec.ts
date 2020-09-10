/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import RoleAccessComponentsPage from './role-access-my-suffix.page-object';
import { RoleAccessDeleteDialog } from './role-access-my-suffix.page-object';
import RoleAccessUpdatePage from './role-access-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('RoleAccess e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let roleAccessUpdatePage: RoleAccessUpdatePage;
  let roleAccessComponentsPage: RoleAccessComponentsPage;
  let roleAccessDeleteDialog: RoleAccessDeleteDialog;

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

  it('should load RoleAccesses', async () => {
    await navBarPage.getEntityPage('role-access-my-suffix');
    roleAccessComponentsPage = new RoleAccessComponentsPage();
    expect(await roleAccessComponentsPage.getTitle().getText()).to.match(/Role Accesses/);
  });

  it('should load create RoleAccess page', async () => {
    await roleAccessComponentsPage.clickOnCreateButton();
    roleAccessUpdatePage = new RoleAccessUpdatePage();
    expect(await roleAccessUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.roleAccess.home.createOrEditLabel/);
    await roleAccessUpdatePage.cancel();
  });

  it('should create and save RoleAccesses', async () => {
    async function createRoleAccess() {
      await roleAccessComponentsPage.clickOnCreateButton();
      const selectedCreate = await roleAccessUpdatePage.getCreateInput().isSelected();
      if (selectedCreate) {
        await roleAccessUpdatePage.getCreateInput().click();
        expect(await roleAccessUpdatePage.getCreateInput().isSelected()).to.be.false;
      } else {
        await roleAccessUpdatePage.getCreateInput().click();
        expect(await roleAccessUpdatePage.getCreateInput().isSelected()).to.be.true;
      }
      const selectedRead = await roleAccessUpdatePage.getReadInput().isSelected();
      if (selectedRead) {
        await roleAccessUpdatePage.getReadInput().click();
        expect(await roleAccessUpdatePage.getReadInput().isSelected()).to.be.false;
      } else {
        await roleAccessUpdatePage.getReadInput().click();
        expect(await roleAccessUpdatePage.getReadInput().isSelected()).to.be.true;
      }
      const selectedUpdate = await roleAccessUpdatePage.getUpdateInput().isSelected();
      if (selectedUpdate) {
        await roleAccessUpdatePage.getUpdateInput().click();
        expect(await roleAccessUpdatePage.getUpdateInput().isSelected()).to.be.false;
      } else {
        await roleAccessUpdatePage.getUpdateInput().click();
        expect(await roleAccessUpdatePage.getUpdateInput().isSelected()).to.be.true;
      }
      const selectedDel = await roleAccessUpdatePage.getDelInput().isSelected();
      if (selectedDel) {
        await roleAccessUpdatePage.getDelInput().click();
        expect(await roleAccessUpdatePage.getDelInput().isSelected()).to.be.false;
      } else {
        await roleAccessUpdatePage.getDelInput().click();
        expect(await roleAccessUpdatePage.getDelInput().isSelected()).to.be.true;
      }
      await roleAccessUpdatePage.roleSelectLastOption();
      await roleAccessUpdatePage.featureSelectLastOption();
      await waitUntilDisplayed(roleAccessUpdatePage.getSaveButton());
      await roleAccessUpdatePage.save();
      await waitUntilHidden(roleAccessUpdatePage.getSaveButton());
      expect(await roleAccessUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createRoleAccess();
    await roleAccessComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await roleAccessComponentsPage.countDeleteButtons();
    await createRoleAccess();

    await roleAccessComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await roleAccessComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last RoleAccess', async () => {
    await roleAccessComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await roleAccessComponentsPage.countDeleteButtons();
    await roleAccessComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    roleAccessDeleteDialog = new RoleAccessDeleteDialog();
    expect(await roleAccessDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.roleAccess.delete.question/);
    await roleAccessDeleteDialog.clickOnConfirmButton();

    await roleAccessComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await roleAccessComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
