import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CoursesMySuffix from './courses-my-suffix';
import CoursesMySuffixDetail from './courses-my-suffix-detail';
import CoursesMySuffixUpdate from './courses-my-suffix-update';
import CoursesMySuffixDeleteDialog from './courses-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CoursesMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CoursesMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CoursesMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={CoursesMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CoursesMySuffixDeleteDialog} />
  </>
);

export default Routes;
