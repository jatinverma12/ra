import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CentersMySuffix from './centers-my-suffix';
import CentersMySuffixDetail from './centers-my-suffix-detail';
import CentersMySuffixUpdate from './centers-my-suffix-update';
import CentersMySuffixDeleteDialog from './centers-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CentersMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CentersMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CentersMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={CentersMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CentersMySuffixDeleteDialog} />
  </>
);

export default Routes;
