import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CenterHeadMySuffix from './center-head-my-suffix';
import CenterHeadMySuffixDetail from './center-head-my-suffix-detail';
import CenterHeadMySuffixUpdate from './center-head-my-suffix-update';
import CenterHeadMySuffixDeleteDialog from './center-head-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CenterHeadMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CenterHeadMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CenterHeadMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={CenterHeadMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CenterHeadMySuffixDeleteDialog} />
  </>
);

export default Routes;
