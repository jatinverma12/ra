export const enum QuestionLevel {
  BEGINNERS = 'BEGINNERS',
  MODERATE = 'MODERATE',
  ADVANCE = 'ADVANCE'
}

export interface IQuestionsMySuffix {
  id?: number;
  question?: any;
  diagramContentType?: string;
  diagram?: any;
  option1?: string;
  option2?: string;
  option3?: string;
  option4?: string;
  answer?: string;
  maxMarks?: number;
  negativeMarks?: number;
  level?: QuestionLevel;
  courseCourse?: string;
  courseId?: number;
  subjectSubjectTitle?: string;
  subjectId?: number;
  chapterChapterTitle?: string;
  chapterId?: number;
}

export const defaultValue: Readonly<IQuestionsMySuffix> = {};
