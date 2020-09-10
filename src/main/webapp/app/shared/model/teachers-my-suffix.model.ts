import { ISubjectsMySuffix } from 'app/shared/model/subjects-my-suffix.model';
import { ICoursesMySuffix } from 'app/shared/model/courses-my-suffix.model';

export interface ITeachersMySuffix {
  id?: number;
  teacherEmployeeId?: string;
  teacherId?: number;
  subjects?: ISubjectsMySuffix[];
  courses?: ICoursesMySuffix[];
}

export const defaultValue: Readonly<ITeachersMySuffix> = {};
