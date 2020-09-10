export interface IUserPreferencesMySuffix {
  id?: number;
  theme?: string;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IUserPreferencesMySuffix> = {};
