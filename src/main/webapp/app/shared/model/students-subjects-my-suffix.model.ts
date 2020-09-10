import { ISubjectsMySuffix } from 'app/shared/model/subjects-my-suffix.model';
import { ICoursesMySuffix } from 'app/shared/model/courses-my-suffix.model';

export const enum Month {
  JAN = 'JAN',
  FEB = 'FEB',
  MAR = 'MAR',
  APR = 'APR',
  MAY = 'MAY',
  JUN = 'JUN',
  JUL = 'JUL',
  AUG = 'AUG',
  SEP = 'SEP',
  OCT = 'OCT',
  NOV = 'NOV',
  DEC = 'DEC'
}

export interface IStudentsSubjectsMySuffix {
  id?: number;
  month?: Month;
  registrationnoStudentRegId?: string;
  registrationnoId?: number;
  sessionAcadSession?: string;
  sessionId?: number;
  subjects?: ISubjectsMySuffix[];
  courses?: ICoursesMySuffix[];
}

export const defaultValue: Readonly<IStudentsSubjectsMySuffix> = {};
