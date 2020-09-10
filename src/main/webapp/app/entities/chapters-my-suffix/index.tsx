import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ChaptersMySuffix from './chapters-my-suffix';
import ChaptersMySuffixDetail from './chapters-my-suffix-detail';
import ChaptersMySuffixUpdate from './chapters-my-suffix-update';
import ChaptersMySuffixDeleteDialog from './chapters-my-suffix-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ChaptersMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ChaptersMySuffixUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ChaptersMySuffixDetail} />
      <ErrorBoundaryRoute path={match.url} component={ChaptersMySuffix} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ChaptersMySuffixDeleteDialog} />
  </>
);

export default Routes;
