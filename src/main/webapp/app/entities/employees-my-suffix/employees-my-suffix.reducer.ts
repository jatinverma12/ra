import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmployeesMySuffix, defaultValue } from 'app/shared/model/employees-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYEES_LIST: 'employees/FETCH_EMPLOYEES_LIST',
  FETCH_EMPLOYEES: 'employees/FETCH_EMPLOYEES',
  CREATE_EMPLOYEES: 'employees/CREATE_EMPLOYEES',
  UPDATE_EMPLOYEES: 'employees/UPDATE_EMPLOYEES',
  DELETE_EMPLOYEES: 'employees/DELETE_EMPLOYEES',
  SET_BLOB: 'employees/SET_BLOB',
  RESET: 'employees/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployeesMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type EmployeesMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: EmployeesMySuffixState = initialState, action): EmployeesMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYEES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYEES):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYEES):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYEES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYEES):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYEES):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYEES):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYEES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYEES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYEES):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYEES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYEES):
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

const apiUrl = 'api/employees';

// Actions

export const getEntities: ICrudGetAllAction<IEmployeesMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EMPLOYEES_LIST,
  payload: axios.get<IEmployeesMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IEmployeesMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYEES,
    payload: axios.get<IEmployeesMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEmployeesMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYEES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmployeesMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYEES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployeesMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYEES,
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
