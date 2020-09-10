/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import DiscountsComponentsPage from './discounts-my-suffix.page-object';
import { DiscountsDeleteDialog } from './discounts-my-suffix.page-object';
import DiscountsUpdatePage from './discounts-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Discounts e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let discountsUpdatePage: DiscountsUpdatePage;
  let discountsComponentsPage: DiscountsComponentsPage;
  let discountsDeleteDialog: DiscountsDeleteDialog;

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

  it('should load Discounts', async () => {
    await navBarPage.getEntityPage('discounts-my-suffix');
    discountsComponentsPage = new DiscountsComponentsPage();
    expect(await discountsComponentsPage.getTitle().getText()).to.match(/Discounts/);
  });

  it('should load create Discounts page', async () => {
    await discountsComponentsPage.clickOnCreateButton();
    discountsUpdatePage = new DiscountsUpdatePage();
    expect(await discountsUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.discounts.home.createOrEditLabel/);
    await discountsUpdatePage.cancel();
  });

  it('should create and save Discounts', async () => {
    async function createDiscounts() {
      await discountsComponentsPage.clickOnCreateButton();
      await discountsUpdatePage.setSubject2Input('5');
      expect(await discountsUpdatePage.getSubject2Input()).to.eq('5');
      await discountsUpdatePage.setSubject3Input('5');
      expect(await discountsUpdatePage.getSubject3Input()).to.eq('5');
      await discountsUpdatePage.setSubject4Input('5');
      expect(await discountsUpdatePage.getSubject4Input()).to.eq('5');
      await discountsUpdatePage.setSubject5Input('5');
      expect(await discountsUpdatePage.getSubject5Input()).to.eq('5');
      await discountsUpdatePage.setSubject6Input('5');
      expect(await discountsUpdatePage.getSubject6Input()).to.eq('5');
      await discountsUpdatePage.setSubject7Input('5');
      expect(await discountsUpdatePage.getSubject7Input()).to.eq('5');
      await discountsUpdatePage.setSubject8Input('5');
      expect(await discountsUpdatePage.getSubject8Input()).to.eq('5');
      await discountsUpdatePage.setQuarterlyInput('5');
      expect(await discountsUpdatePage.getQuarterlyInput()).to.eq('5');
      await discountsUpdatePage.setHalfYearlyInput('5');
      expect(await discountsUpdatePage.getHalfYearlyInput()).to.eq('5');
      await discountsUpdatePage.setAnnuallyInput('5');
      expect(await discountsUpdatePage.getAnnuallyInput()).to.eq('5');
      await discountsUpdatePage.setSiblingInput('5');
      expect(await discountsUpdatePage.getSiblingInput()).to.eq('5');
      await discountsUpdatePage.setReferralInput('5');
      expect(await discountsUpdatePage.getReferralInput()).to.eq('5');
      await discountsUpdatePage.sessionSelectLastOption();
      await waitUntilDisplayed(discountsUpdatePage.getSaveButton());
      await discountsUpdatePage.save();
      await waitUntilHidden(discountsUpdatePage.getSaveButton());
      expect(await discountsUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createDiscounts();
    await discountsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await discountsComponentsPage.countDeleteButtons();
    await createDiscounts();

    await discountsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await discountsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Discounts', async () => {
    await discountsComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await discountsComponentsPage.countDeleteButtons();
    await discountsComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    discountsDeleteDialog = new DiscountsDeleteDialog();
    expect(await discountsDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.discounts.delete.question/);
    await discountsDeleteDialog.clickOnConfirmButton();

    await discountsComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await discountsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
