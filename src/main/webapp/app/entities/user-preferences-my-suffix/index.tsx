import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserPreferencesMySuffix from './user-preferences-my-suffix';
import UserPreferencesMySuffixDetail from './user-preferences-my-suffix-detail';
import UserPreferencesMySuffixUpdate from './user-preferences-my-suffix-update';
import UserPreferencesMySuffixDeleteDialog from './user-preferences-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserPreferencesMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserPreferencesMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserPreferencesMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserPreferencesMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UserPreferencesMySuffixDeleteDialog} />
  </>
);

export default Routes;
