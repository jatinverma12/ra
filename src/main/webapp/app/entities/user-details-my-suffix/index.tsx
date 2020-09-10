import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserDetailsMySuffix from './user-details-my-suffix';
import UserDetailsMySuffixDetail from './user-details-my-suffix-detail';
import UserDetailsMySuffixUpdate from './user-details-my-suffix-update';
import UserDetailsMySuffixDeleteDialog from './user-details-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserDetailsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserDetailsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserDetailsMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserDetailsMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UserDetailsMySuffixDeleteDialog} />
  </>
);

export default Routes;
