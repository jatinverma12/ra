import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StudentFeesMySuffix from './student-fees-my-suffix';
import StudentFeesMySuffixDetail from './student-fees-my-suffix-detail';
import StudentFeesMySuffixUpdate from './student-fees-my-suffix-update';
import StudentFeesMySuffixDeleteDialog from './student-fees-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StudentFeesMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StudentFeesMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StudentFeesMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={StudentFeesMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={StudentFeesMySuffixDeleteDialog} />
  </>
);

export default Routes;
