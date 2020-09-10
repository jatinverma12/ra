import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStudentFeesMySuffix, defaultValue } from 'app/shared/model/student-fees-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_STUDENTFEES_LIST: 'studentFees/FETCH_STUDENTFEES_LIST',
  FETCH_STUDENTFEES: 'studentFees/FETCH_STUDENTFEES',
  CREATE_STUDENTFEES: 'studentFees/CREATE_STUDENTFEES',
  UPDATE_STUDENTFEES: 'studentFees/UPDATE_STUDENTFEES',
  DELETE_STUDENTFEES: 'studentFees/DELETE_STUDENTFEES',
  RESET: 'studentFees/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStudentFeesMySuffix>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type StudentFeesMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: StudentFeesMySuffixState = initialState, action): StudentFeesMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_STUDENTFEES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STUDENTFEES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_STUDENTFEES):
    case REQUEST(ACTION_TYPES.UPDATE_STUDENTFEES):
    case REQUEST(ACTION_TYPES.DELETE_STUDENTFEES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_STUDENTFEES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STUDENTFEES):
    case FAILURE(ACTION_TYPES.CREATE_STUDENTFEES):
    case FAILURE(ACTION_TYPES.UPDATE_STUDENTFEES):
    case FAILURE(ACTION_TYPES.DELETE_STUDENTFEES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_STUDENTFEES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_STUDENTFEES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_STUDENTFEES):
    case SUCCESS(ACTION_TYPES.UPDATE_STUDENTFEES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_STUDENTFEES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/student-fees';

// Actions

export const getEntities: ICrudGetAllAction<IStudentFeesMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_STUDENTFEES_LIST,
    payload: axios.get<IStudentFeesMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IStudentFeesMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STUDENTFEES,
    payload: axios.get<IStudentFeesMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IStudentFeesMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STUDENTFEES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStudentFeesMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STUDENTFEES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStudentFeesMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STUDENTFEES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
