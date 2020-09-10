import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISubjectsBaseFeeMySuffix, defaultValue } from 'app/shared/model/subjects-base-fee-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_SUBJECTSBASEFEE_LIST: 'subjectsBaseFee/FETCH_SUBJECTSBASEFEE_LIST',
  FETCH_SUBJECTSBASEFEE: 'subjectsBaseFee/FETCH_SUBJECTSBASEFEE',
  CREATE_SUBJECTSBASEFEE: 'subjectsBaseFee/CREATE_SUBJECTSBASEFEE',
  UPDATE_SUBJECTSBASEFEE: 'subjectsBaseFee/UPDATE_SUBJECTSBASEFEE',
  DELETE_SUBJECTSBASEFEE: 'subjectsBaseFee/DELETE_SUBJECTSBASEFEE',
  RESET: 'subjectsBaseFee/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISubjectsBaseFeeMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SubjectsBaseFeeMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: SubjectsBaseFeeMySuffixState = initialState, action): SubjectsBaseFeeMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SUBJECTSBASEFEE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUBJECTSBASEFEE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SUBJECTSBASEFEE):
    case REQUEST(ACTION_TYPES.UPDATE_SUBJECTSBASEFEE):
    case REQUEST(ACTION_TYPES.DELETE_SUBJECTSBASEFEE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SUBJECTSBASEFEE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUBJECTSBASEFEE):
    case FAILURE(ACTION_TYPES.CREATE_SUBJECTSBASEFEE):
    case FAILURE(ACTION_TYPES.UPDATE_SUBJECTSBASEFEE):
    case FAILURE(ACTION_TYPES.DELETE_SUBJECTSBASEFEE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBJECTSBASEFEE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBJECTSBASEFEE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUBJECTSBASEFEE):
    case SUCCESS(ACTION_TYPES.UPDATE_SUBJECTSBASEFEE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUBJECTSBASEFEE):
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

const apiUrl = 'api/subjects-base-fees';

// Actions

export const getEntities: ICrudGetAllAction<ISubjectsBaseFeeMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SUBJECTSBASEFEE_LIST,
  payload: axios.get<ISubjectsBaseFeeMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ISubjectsBaseFeeMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUBJECTSBASEFEE,
    payload: axios.get<ISubjectsBaseFeeMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISubjectsBaseFeeMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUBJECTSBASEFEE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISubjectsBaseFeeMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUBJECTSBASEFEE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISubjectsBaseFeeMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUBJECTSBASEFEE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
