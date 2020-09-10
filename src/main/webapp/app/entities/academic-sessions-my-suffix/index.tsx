import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AcademicSessionsMySuffix from './academic-sessions-my-suffix';
import AcademicSessionsMySuffixDetail from './academic-sessions-my-suffix-detail';
import AcademicSessionsMySuffixUpdate from './academic-sessions-my-suffix-update';
import AcademicSessionsMySuffixDeleteDialog from './academic-sessions-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AcademicSessionsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AcademicSessionsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AcademicSessionsMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={AcademicSessionsMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AcademicSessionsMySuffixDeleteDialog} />
  </>
);

export default Routes;
