import { Moment } from 'moment';

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

export interface IUserDetailsMySuffix {
  id?: number;
  mobileNo?: string;
  dob?: Moment;
  houseNo?: string;
  streetNo?: number;
  city?: City;
  state?: State;
  pincode?: number;
  userLogin?: string;
  userId?: number;
}

export const defaultValue: Readonly<IUserDetailsMySuffix> = {};
