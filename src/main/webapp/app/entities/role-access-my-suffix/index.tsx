import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RoleAccessMySuffix from './role-access-my-suffix';
import RoleAccessMySuffixDetail from './role-access-my-suffix-detail';
import RoleAccessMySuffixUpdate from './role-access-my-suffix-update';
import RoleAccessMySuffixDeleteDialog from './role-access-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RoleAccessMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RoleAccessMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RoleAccessMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={RoleAccessMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RoleAccessMySuffixDeleteDialog} />
  </>
);

export default Routes;
