import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FeaturesMySuffix from './features-my-suffix';
import FeaturesMySuffixDetail from './features-my-suffix-detail';
import FeaturesMySuffixUpdate from './features-my-suffix-update';
import FeaturesMySuffixDeleteDialog from './features-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FeaturesMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FeaturesMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FeaturesMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={FeaturesMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={FeaturesMySuffixDeleteDialog} />
  </>
);

export default Routes;
