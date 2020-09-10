import { ICenterHeadMySuffix } from 'app/shared/model/center-head-my-suffix.model';

export const enum City {
  GURGAON = 'GURGAON',
  DELHI = 'DELHI',
  DEHRADUN = 'DEHRADUN'
}

export const enum State {
  DELHI = 'DELHI',
  HARYANA = 'HARYANA',
  UTTRAKHAND = 'UTTRAKHAND',
  MADHYAPRADESH = 'MADHYAPRADESH'
}

export interface ICentersMySuffix {
  id?: number;
  centerCode?: string;
  centerTitle?: string;
  streetNo?: number;
  city?: City;
  state?: State;
  pincode?: number;
  centerheads?: ICenterHeadMySuffix[];
}

export const defaultValue: Readonly<ICentersMySuffix> = {};
