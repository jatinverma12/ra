import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ExpensesMySuffix from './expenses-my-suffix';
import ExpensesMySuffixDetail from './expenses-my-suffix-detail';
import ExpensesMySuffixUpdate from './expenses-my-suffix-update';
import ExpensesMySuffixDeleteDialog from './expenses-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExpensesMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExpensesMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExpensesMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ExpensesMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ExpensesMySuffixDeleteDialog} />
  </>
);

export default Routes;
