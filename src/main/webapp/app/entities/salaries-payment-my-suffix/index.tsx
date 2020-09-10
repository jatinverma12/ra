import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SalariesPaymentMySuffix from './salaries-payment-my-suffix';
import SalariesPaymentMySuffixDetail from './salaries-payment-my-suffix-detail';
import SalariesPaymentMySuffixUpdate from './salaries-payment-my-suffix-update';
import SalariesPaymentMySuffixDeleteDialog from './salaries-payment-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SalariesPaymentMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SalariesPaymentMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SalariesPaymentMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={SalariesPaymentMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SalariesPaymentMySuffixDeleteDialog} />
  </>
);

export default Routes;
