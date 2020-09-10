import { ICoursesMySuffix } from 'app/shared/model/courses-my-suffix.model';

export const enum StudentStatus {
  GRADUATED = 'GRADUATED',
  JOINED = 'JOINED',
  LEFT = 'LEFT'
}

export const enum LeavingReasons {
  DISTANCEFACTOR = 'DISTANCEFACTOR',
  UNHAPPYPHYSICS = 'UNHAPPYPHYSICS',
  UNHAPPYMATHS = 'UNHAPPYMATHS',
  UNHAPPYBIO = 'UNHAPPYBIO',
  UNHAPPYCHEMISTRY = 'UNHAPPYCHEMISTRY',
  UNHAPPYMANAGEMENT = 'UNHAPPYMANAGEMENT',
  HIGHFEES = 'HIGHFEES',
  CLASSESOVERLAP = 'CLASSESOVERLAP',
  COURSECOMPLETED = 'COURSECOMPLETED',
  BREAKEXAM = 'BREAKEXAM',
  BREAKHOLIDAY = 'BREAKHOLIDAY',
  PERSONALREASON = 'PERSONALREASON'
}

export const enum InfoSources {
  LOCATIONDIRECTLY = 'LOCATIONDIRECTLY',
  FRIENDS = 'FRIENDS',
  BANNER = 'BANNER',
  INTERNET = 'INTERNET',
  PAMPHLET = 'PAMPHLET',
  NEWSPAPER = 'NEWSPAPER'
}

export interface IStudentsMySuffix {
  id?: number;
  studentRegId?: string;
  registrationFormContentType?: string;
  registrationForm?: any;
  parentMobNo1?: string;
  parentMobNo2?: string;
  parentEmailId?: string;
  studentStatus?: StudentStatus;
  leavingReason?: LeavingReasons;
  infoSource?: InfoSources;
  userLogin?: string;
  userId?: number;
  courses?: ICoursesMySuffix[];
}

export const defaultValue: Readonly<IStudentsMySuffix> = {};
