import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StudentsMySuffix from './students-my-suffix';
import StudentsMySuffixDetail from './students-my-suffix-detail';
import StudentsMySuffixUpdate from './students-my-suffix-update';
import StudentsMySuffixDeleteDialog from './students-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StudentsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StudentsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StudentsMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={StudentsMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={StudentsMySuffixDeleteDialog} />
  </>
);

export default Routes;
