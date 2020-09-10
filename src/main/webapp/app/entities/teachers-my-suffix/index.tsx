import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TeachersMySuffix from './teachers-my-suffix';
import TeachersMySuffixDetail from './teachers-my-suffix-detail';
import TeachersMySuffixUpdate from './teachers-my-suffix-update';
import TeachersMySuffixDeleteDialog from './teachers-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TeachersMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TeachersMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TeachersMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={TeachersMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={TeachersMySuffixDeleteDialog} />
  </>
);

export default Routes;
