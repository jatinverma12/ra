import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SubjectsMySuffix from './subjects-my-suffix';
import SubjectsMySuffixDetail from './subjects-my-suffix-detail';
import SubjectsMySuffixUpdate from './subjects-my-suffix-update';
import SubjectsMySuffixDeleteDialog from './subjects-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SubjectsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SubjectsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SubjectsMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={SubjectsMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SubjectsMySuffixDeleteDialog} />
  </>
);

export default Routes;
