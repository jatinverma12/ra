import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import StudentsSubjectsMySuffix from './students-subjects-my-suffix';
import StudentsSubjectsMySuffixDetail from './students-subjects-my-suffix-detail';
import StudentsSubjectsMySuffixUpdate from './students-subjects-my-suffix-update';
import StudentsSubjectsMySuffixDeleteDialog from './students-subjects-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StudentsSubjectsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StudentsSubjectsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StudentsSubjectsMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={StudentsSubjectsMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={StudentsSubjectsMySuffixDeleteDialog} />
  </>
);

export default Routes;
