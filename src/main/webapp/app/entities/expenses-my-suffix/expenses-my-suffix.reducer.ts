import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IExpensesMySuffix, defaultValue } from 'app/shared/model/expenses-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_EXPENSES_LIST: 'expenses/FETCH_EXPENSES_LIST',
  FETCH_EXPENSES: 'expenses/FETCH_EXPENSES',
  CREATE_EXPENSES: 'expenses/CREATE_EXPENSES',
  UPDATE_EXPENSES: 'expenses/UPDATE_EXPENSES',
  DELETE_EXPENSES: 'expenses/DELETE_EXPENSES',
  SET_BLOB: 'expenses/SET_BLOB',
  RESET: 'expenses/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IExpensesMySuffix>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ExpensesMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ExpensesMySuffixState = initialState, action): ExpensesMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EXPENSES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EXPENSES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EXPENSES):
    case REQUEST(ACTION_TYPES.UPDATE_EXPENSES):
    case REQUEST(ACTION_TYPES.DELETE_EXPENSES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EXPENSES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EXPENSES):
    case FAILURE(ACTION_TYPES.CREATE_EXPENSES):
    case FAILURE(ACTION_TYPES.UPDATE_EXPENSES):
    case FAILURE(ACTION_TYPES.DELETE_EXPENSES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXPENSES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_EXPENSES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EXPENSES):
    case SUCCESS(ACTION_TYPES.UPDATE_EXPENSES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EXPENSES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/expenses';

// Actions

export const getEntities: ICrudGetAllAction<IExpensesMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_EXPENSES_LIST,
    payload: axios.get<IExpensesMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IExpensesMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EXPENSES,
    payload: axios.get<IExpensesMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IExpensesMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EXPENSES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IExpensesMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EXPENSES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IExpensesMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EXPENSES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
