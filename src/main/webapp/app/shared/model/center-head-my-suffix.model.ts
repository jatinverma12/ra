import { ICentersMySuffix } from 'app/shared/model/centers-my-suffix.model';

export interface ICenterHeadMySuffix {
  id?: number;
  centerheadEmployeeId?: string;
  centerheadId?: number;
  centers?: ICentersMySuffix[];
}

export const defaultValue: Readonly<ICenterHeadMySuffix> = {};
