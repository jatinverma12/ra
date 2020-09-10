import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ScholarshipsMySuffix from './scholarships-my-suffix';
import ScholarshipsMySuffixDetail from './scholarships-my-suffix-detail';
import ScholarshipsMySuffixUpdate from './scholarships-my-suffix-update';
import ScholarshipsMySuffixDeleteDialog from './scholarships-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ScholarshipsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ScholarshipsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ScholarshipsMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ScholarshipsMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ScholarshipsMySuffixDeleteDialog} />
  </>
);

export default Routes;
