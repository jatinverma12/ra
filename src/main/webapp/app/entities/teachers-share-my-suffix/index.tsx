import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TeachersShareMySuffix from './teachers-share-my-suffix';
import TeachersShareMySuffixDetail from './teachers-share-my-suffix-detail';
import TeachersShareMySuffixUpdate from './teachers-share-my-suffix-update';
import TeachersShareMySuffixDeleteDialog from './teachers-share-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TeachersShareMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TeachersShareMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TeachersShareMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={TeachersShareMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TeachersShareMySuffixDeleteDialog} />
  </>
);

export default Routes;
