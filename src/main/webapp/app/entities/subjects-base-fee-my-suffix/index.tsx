import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SubjectsBaseFeeMySuffix from './subjects-base-fee-my-suffix';
import SubjectsBaseFeeMySuffixDetail from './subjects-base-fee-my-suffix-detail';
import SubjectsBaseFeeMySuffixUpdate from './subjects-base-fee-my-suffix-update';
import SubjectsBaseFeeMySuffixDeleteDialog from './subjects-base-fee-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SubjectsBaseFeeMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SubjectsBaseFeeMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SubjectsBaseFeeMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={SubjectsBaseFeeMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SubjectsBaseFeeMySuffixDeleteDialog} />
  </>
);

export default Routes;
