/* tslint:disable no-unused-expression */
import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import FeaturesComponentsPage from './features-my-suffix.page-object';
import { FeaturesDeleteDialog } from './features-my-suffix.page-object';
import FeaturesUpdatePage from './features-my-suffix-update.page-object';
import { waitUntilDisplayed, waitUntilHidden } from '../../util/utils';

const expect = chai.expect;

describe('Features e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let featuresUpdatePage: FeaturesUpdatePage;
  let featuresComponentsPage: FeaturesComponentsPage;
  let featuresDeleteDialog: FeaturesDeleteDialog;

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

  it('should load Features', async () => {
    await navBarPage.getEntityPage('features-my-suffix');
    featuresComponentsPage = new FeaturesComponentsPage();
    expect(await featuresComponentsPage.getTitle().getText()).to.match(/Features/);
  });

  it('should load create Features page', async () => {
    await featuresComponentsPage.clickOnCreateButton();
    featuresUpdatePage = new FeaturesUpdatePage();
    expect(await featuresUpdatePage.getPageTitle().getAttribute('id')).to.match(/risingArjunApp.features.home.createOrEditLabel/);
    await featuresUpdatePage.cancel();
  });

  it('should create and save Features', async () => {
    async function createFeatures() {
      await featuresComponentsPage.clickOnCreateButton();
      await featuresUpdatePage.setFeatureIdInput('featureId');
      expect(await featuresUpdatePage.getFeatureIdInput()).to.match(/featureId/);
      await featuresUpdatePage.setFeatureDetailInput('featureDetail');
      expect(await featuresUpdatePage.getFeatureDetailInput()).to.match(/featureDetail/);
      await waitUntilDisplayed(featuresUpdatePage.getSaveButton());
      await featuresUpdatePage.save();
      await waitUntilHidden(featuresUpdatePage.getSaveButton());
      expect(await featuresUpdatePage.getSaveButton().isPresent()).to.be.false;
    }

    await createFeatures();
    await featuresComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeCreate = await featuresComponentsPage.countDeleteButtons();
    await createFeatures();

    await featuresComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeCreate + 1);
    expect(await featuresComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
  });

  it('should delete last Features', async () => {
    await featuresComponentsPage.waitUntilLoaded();
    const nbButtonsBeforeDelete = await featuresComponentsPage.countDeleteButtons();
    await featuresComponentsPage.clickOnLastDeleteButton();

    const deleteModal = element(by.className('modal'));
    await waitUntilDisplayed(deleteModal);

    featuresDeleteDialog = new FeaturesDeleteDialog();
    expect(await featuresDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/risingArjunApp.features.delete.question/);
    await featuresDeleteDialog.clickOnConfirmButton();

    await featuresComponentsPage.waitUntilDeleteButtonsLength(nbButtonsBeforeDelete - 1);
    expect(await featuresComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
