import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Blog from './blog';
import Entry from './entry';
import Tag from './tag';
import JhiauthorityMySuffix from './jhiauthority-my-suffix';
import FeaturesMySuffix from './features-my-suffix';
import RoleAccessMySuffix from './role-access-my-suffix';
import UserDetailsMySuffix from './user-details-my-suffix';
import UserPreferencesMySuffix from './user-preferences-my-suffix';
import CoursesMySuffix from './courses-my-suffix';
import StudentsMySuffix from './students-my-suffix';
import SubjectsMySuffix from './subjects-my-suffix';
import AcademicSessionsMySuffix from './academic-sessions-my-suffix';
import StudentsSubjectsMySuffix from './students-subjects-my-suffix';
import SubjectsBaseFeeMySuffix from './subjects-base-fee-my-suffix';
import DiscountsMySuffix from './discounts-my-suffix';
import ScholarshipsMySuffix from './scholarships-my-suffix';
import StudentFeesMySuffix from './student-fees-my-suffix';
import EmployeesMySuffix from './employees-my-suffix';
import CentersMySuffix from './centers-my-suffix';
import CenterHeadMySuffix from './center-head-my-suffix';
import TeachersMySuffix from './teachers-my-suffix';
import TeachersShareMySuffix from './teachers-share-my-suffix';
import SalariesPaymentMySuffix from './salaries-payment-my-suffix';
import ExpensesMySuffix from './expenses-my-suffix';
import ChaptersMySuffix from './chapters-my-suffix';
import QuestionsMySuffix from './questions-my-suffix';
import StudentScoreMySuffix from './student-score-my-suffix';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/blog`} component={Blog} />
      <ErrorBoundaryRoute path={`${match.url}/entry`} component={Entry} />
      <ErrorBoundaryRoute path={`${match.url}/tag`} component={Tag} />
      <ErrorBoundaryRoute path={`${match.url}/jhiauthority-my-suffix`} component={JhiauthorityMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/features-my-suffix`} component={FeaturesMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/role-access-my-suffix`} component={RoleAccessMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/user-details-my-suffix`} component={UserDetailsMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/user-preferences-my-suffix`} component={UserPreferencesMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/courses-my-suffix`} component={CoursesMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/students-my-suffix`} component={StudentsMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/subjects-my-suffix`} component={SubjectsMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/academic-sessions-my-suffix`} component={AcademicSessionsMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/students-subjects-my-suffix`} component={StudentsSubjectsMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/subjects-base-fee-my-suffix`} component={SubjectsBaseFeeMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/discounts-my-suffix`} component={DiscountsMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/scholarships-my-suffix`} component={ScholarshipsMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/student-fees-my-suffix`} component={StudentFeesMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/employees-my-suffix`} component={EmployeesMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/centers-my-suffix`} component={CentersMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/center-head-my-suffix`} component={CenterHeadMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/teachers-my-suffix`} component={TeachersMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/teachers-share-my-suffix`} component={TeachersShareMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/salaries-payment-my-suffix`} component={SalariesPaymentMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/expenses-my-suffix`} component={ExpensesMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/chapters-my-suffix`} component={ChaptersMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/questions-my-suffix`} component={QuestionsMySuffix} />
      <ErrorBoundaryRoute path={`${match.url}/student-score-my-suffix`} component={StudentScoreMySuffix} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
