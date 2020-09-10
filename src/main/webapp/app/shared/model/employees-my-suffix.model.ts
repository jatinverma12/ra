export const enum JobNature {
  PARTTIME = 'PARTTIME',
  FULLTIME = 'FULLTIME'
}

export interface IEmployeesMySuffix {
  id?: number;
  employeeId?: string;
  jobNature?: JobNature;
  bgc?: boolean;
  resumeContentType?: string;
  resume?: any;
  pan?: string;
  accountNo?: string;
  bank?: string;
  ifsc?: string;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IEmployeesMySuffix> = {
  bgc: false
};
