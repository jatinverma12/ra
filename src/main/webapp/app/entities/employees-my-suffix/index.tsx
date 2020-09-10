import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import EmployeesMySuffix from './employees-my-suffix';
import EmployeesMySuffixDetail from './employees-my-suffix-detail';
import EmployeesMySuffixUpdate from './employees-my-suffix-update';
import EmployeesMySuffixDeleteDialog from './employees-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmployeesMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmployeesMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmployeesMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={EmployeesMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={EmployeesMySuffixDeleteDialog} />
  </>
);

export default Routes;
