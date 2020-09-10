import { Moment } from 'moment';

export const enum Mode {
  NEFT = 'NEFT',
  UPI = 'UPI',
  CASH = 'CASH',
  DEBITCARD = 'DEBITCARD',
  NETBANKING = 'NETBANKING',
  OTHERS = 'OTHERS'
}

export interface ISalariesPaymentMySuffix {
  id?: number;
  salary?: number;
  paid?: number;
  unpaid?: number;
  date?: Moment;
  transactionId?: string;
  paymentMode?: Mode;
  remarks?: string;
  employeeEmployeeId?: string;
  employeeId?: number;
}

export const defaultValue: Readonly<ISalariesPaymentMySuffix> = {};
