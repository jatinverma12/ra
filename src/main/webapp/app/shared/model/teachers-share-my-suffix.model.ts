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

export interface ITeachersShareMySuffix {
  id?: number;
  share?: number;
  plannedClasses?: number;
  actualClasses?: number;
  shareCorrection?: number;
  month?: Month;
  remarks?: string;
  teacherEmployeeId?: string;
  teacherId?: number;
  subjectSubjectTitle?: string;
  subjectId?: number;
  courseCourse?: string;
  courseId?: number;
  sessionAcadSessionId?: string;
  sessionId?: number;
}

export const defaultValue: Readonly<ITeachersShareMySuffix> = {};
