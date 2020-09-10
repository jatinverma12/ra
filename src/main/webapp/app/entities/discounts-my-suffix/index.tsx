import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DiscountsMySuffix from './discounts-my-suffix';
import DiscountsMySuffixDetail from './discounts-my-suffix-detail';
import DiscountsMySuffixUpdate from './discounts-my-suffix-update';
import DiscountsMySuffixDeleteDialog from './discounts-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DiscountsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DiscountsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DiscountsMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={DiscountsMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DiscountsMySuffixDeleteDialog} />
  </>
);

export default Routes;
