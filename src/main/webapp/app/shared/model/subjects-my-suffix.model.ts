import { IStudentsSubjectsMySuffix } from 'app/shared/model/students-subjects-my-suffix.model';
import { ITeachersMySuffix } from 'app/shared/model/teachers-my-suffix.model';

export interface ISubjectsMySuffix {
  id?: number;
  subjectCode?: string;
  subjectTitle?: string;
  studentsubjects?: IStudentsSubjectsMySuffix[];
  teachers?: ITeachersMySuffix[];
}

export const defaultValue: Readonly<ISubjectsMySuffix> = {};
