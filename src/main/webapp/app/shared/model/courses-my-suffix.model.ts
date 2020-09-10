import { IStudentsMySuffix } from 'app/shared/model/students-my-suffix.model';
import { IStudentsSubjectsMySuffix } from 'app/shared/model/students-subjects-my-suffix.model';
import { ITeachersMySuffix } from 'app/shared/model/teachers-my-suffix.model';

export interface ICoursesMySuffix {
  id?: number;
  courseId?: string;
  course?: string;
  students?: IStudentsMySuffix[];
  studentsubjects?: IStudentsSubjectsMySuffix[];
  teachers?: ITeachersMySuffix[];
}

export const defaultValue: Readonly<ICoursesMySuffix> = {};
