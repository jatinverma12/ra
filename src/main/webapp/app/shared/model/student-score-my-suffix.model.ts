import { Moment } from 'moment';

export interface IStudentScoreMySuffix {
  id?: number;
  answer?: string;
  score?: number;
  date?: Moment;
  studentStudentRegId?: string;
  studentId?: number;
  questionIdId?: number;
}

export const defaultValue: Readonly<IStudentScoreMySuffix> = {};
