import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import blog, {
  BlogState
} from 'app/entities/blog/blog.reducer';
// prettier-ignore
import entry, {
  EntryState
} from 'app/entities/entry/entry.reducer';
// prettier-ignore
import tag, {
  TagState
} from 'app/entities/tag/tag.reducer';
// prettier-ignore
import jhiauthority, {
  JhiauthorityMySuffixState
} from 'app/entities/jhiauthority-my-suffix/jhiauthority-my-suffix.reducer';
// prettier-ignore
import features, {
  FeaturesMySuffixState
} from 'app/entities/features-my-suffix/features-my-suffix.reducer';
// prettier-ignore
import roleAccess, {
  RoleAccessMySuffixState
} from 'app/entities/role-access-my-suffix/role-access-my-suffix.reducer';
// prettier-ignore
import userDetails, {
  UserDetailsMySuffixState
} from 'app/entities/user-details-my-suffix/user-details-my-suffix.reducer';
// prettier-ignore
import userPreferences, {
  UserPreferencesMySuffixState
} from 'app/entities/user-preferences-my-suffix/user-preferences-my-suffix.reducer';
// prettier-ignore
import courses, {
  CoursesMySuffixState
} from 'app/entities/courses-my-suffix/courses-my-suffix.reducer';
// prettier-ignore
import students, {
  StudentsMySuffixState
} from 'app/entities/students-my-suffix/students-my-suffix.reducer';
// prettier-ignore
import subjects, {
  SubjectsMySuffixState
} from 'app/entities/subjects-my-suffix/subjects-my-suffix.reducer';
// prettier-ignore
import academicSessions, {
  AcademicSessionsMySuffixState
} from 'app/entities/academic-sessions-my-suffix/academic-sessions-my-suffix.reducer';
// prettier-ignore
import studentsSubjects, {
  StudentsSubjectsMySuffixState
} from 'app/entities/students-subjects-my-suffix/students-subjects-my-suffix.reducer';
// prettier-ignore
import subjectsBaseFee, {
  SubjectsBaseFeeMySuffixState
} from 'app/entities/subjects-base-fee-my-suffix/subjects-base-fee-my-suffix.reducer';
// prettier-ignore
import discounts, {
  DiscountsMySuffixState
} from 'app/entities/discounts-my-suffix/discounts-my-suffix.reducer';
// prettier-ignore
import scholarships, {
  ScholarshipsMySuffixState
} from 'app/entities/scholarships-my-suffix/scholarships-my-suffix.reducer';
// prettier-ignore
import studentFees, {
  StudentFeesMySuffixState
} from 'app/entities/student-fees-my-suffix/student-fees-my-suffix.reducer';
// prettier-ignore
import employees, {
  EmployeesMySuffixState
} from 'app/entities/employees-my-suffix/employees-my-suffix.reducer';
// prettier-ignore
import centers, {
  CentersMySuffixState
} from 'app/entities/centers-my-suffix/centers-my-suffix.reducer';
// prettier-ignore
import centerHead, {
  CenterHeadMySuffixState
} from 'app/entities/center-head-my-suffix/center-head-my-suffix.reducer';
// prettier-ignore
import teachers, {
  TeachersMySuffixState
} from 'app/entities/teachers-my-suffix/teachers-my-suffix.reducer';
// prettier-ignore
import teachersShare, {
  TeachersShareMySuffixState
} from 'app/entities/teachers-share-my-suffix/teachers-share-my-suffix.reducer';
// prettier-ignore
import salariesPayment, {
  SalariesPaymentMySuffixState
} from 'app/entities/salaries-payment-my-suffix/salaries-payment-my-suffix.reducer';
// prettier-ignore
import expenses, {
  ExpensesMySuffixState
} from 'app/entities/expenses-my-suffix/expenses-my-suffix.reducer';
// prettier-ignore
import chapters, {
  ChaptersMySuffixState
} from 'app/entities/chapters-my-suffix/chapters-my-suffix.reducer';
// prettier-ignore
import questions, {
  QuestionsMySuffixState
} from 'app/entities/questions-my-suffix/questions-my-suffix.reducer';
// prettier-ignore
import studentScore, {
  StudentScoreMySuffixState
} from 'app/entities/student-score-my-suffix/student-score-my-suffix.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly blog: BlogState;
  readonly entry: EntryState;
  readonly tag: TagState;
  readonly jhiauthority: JhiauthorityMySuffixState;
  readonly features: FeaturesMySuffixState;
  readonly roleAccess: RoleAccessMySuffixState;
  readonly userDetails: UserDetailsMySuffixState;
  readonly userPreferences: UserPreferencesMySuffixState;
  readonly courses: CoursesMySuffixState;
  readonly students: StudentsMySuffixState;
  readonly subjects: SubjectsMySuffixState;
  readonly academicSessions: AcademicSessionsMySuffixState;
  readonly studentsSubjects: StudentsSubjectsMySuffixState;
  readonly subjectsBaseFee: SubjectsBaseFeeMySuffixState;
  readonly discounts: DiscountsMySuffixState;
  readonly scholarships: ScholarshipsMySuffixState;
  readonly studentFees: StudentFeesMySuffixState;
  readonly employees: EmployeesMySuffixState;
  readonly centers: CentersMySuffixState;
  readonly centerHead: CenterHeadMySuffixState;
  readonly teachers: TeachersMySuffixState;
  readonly teachersShare: TeachersShareMySuffixState;
  readonly salariesPayment: SalariesPaymentMySuffixState;
  readonly expenses: ExpensesMySuffixState;
  readonly chapters: ChaptersMySuffixState;
  readonly questions: QuestionsMySuffixState;
  readonly studentScore: StudentScoreMySuffixState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  blog,
  entry,
  tag,
  jhiauthority,
  features,
  roleAccess,
  userDetails,
  userPreferences,
  courses,
  students,
  subjects,
  academicSessions,
  studentsSubjects,
  subjectsBaseFee,
  discounts,
  scholarships,
  studentFees,
  employees,
  centers,
  centerHead,
  teachers,
  teachersShare,
  salariesPayment,
  expenses,
  chapters,
  questions,
  studentScore,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
