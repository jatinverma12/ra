import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import QuestionsMySuffix from './questions-my-suffix';
import QuestionsMySuffixDetail from './questions-my-suffix-detail';
import QuestionsMySuffixUpdate from './questions-my-suffix-update';
import QuestionsMySuffixDeleteDialog from './questions-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={QuestionsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={QuestionsMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={QuestionsMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={QuestionsMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={QuestionsMySuffixDeleteDialog} />
  </>
);

export default Routes;
