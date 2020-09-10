import { Moment } from 'moment';

export const enum Mode {
  NEFT = 'NEFT',
  UPI = 'UPI',
  CASH = 'CASH',
  DEBITCARD = 'DEBITCARD',
  NETBANKING = 'NETBANKING',
  OTHERS = 'OTHERS'
}

export const enum ExpensesType {
  OPERATING = 'OPERATING',
  MARKETING = 'MARKETING',
  FIXASSET = 'FIXASSET'
}

export interface IExpensesMySuffix {
  id?: number;
  item?: string;
  quantity?: number;
  rate?: number;
  laborCost?: number;
  otherExpense?: number;
  total?: number;
  date?: Moment;
  transactionId?: string;
  expenseMode?: Mode;
  type?: ExpensesType;
  billContentType?: string;
  bill?: any;
  remarks?: string;
  incurredByEmployeeId?: string;
  incurredById?: number;
}

export const defaultValue: Readonly<IExpensesMySuffix> = {};
