import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StudentScoreMySuffix from './student-score-my-suffix';
import StudentScoreMySuffixDetail from './student-score-my-suffix-detail';
import StudentScoreMySuffixUpdate from './student-score-my-suffix-update';
import StudentScoreMySuffixDeleteDialog from './student-score-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StudentScoreMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StudentScoreMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StudentScoreMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={StudentScoreMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={StudentScoreMySuffixDeleteDialog} />
  </>
);

export default Routes;
