import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IScholarshipsMySuffix, defaultValue } from 'app/shared/model/scholarships-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_SCHOLARSHIPS_LIST: 'scholarships/FETCH_SCHOLARSHIPS_LIST',
  FETCH_SCHOLARSHIPS: 'scholarships/FETCH_SCHOLARSHIPS',
  CREATE_SCHOLARSHIPS: 'scholarships/CREATE_SCHOLARSHIPS',
  UPDATE_SCHOLARSHIPS: 'scholarships/UPDATE_SCHOLARSHIPS',
  DELETE_SCHOLARSHIPS: 'scholarships/DELETE_SCHOLARSHIPS',
  RESET: 'scholarships/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IScholarshipsMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ScholarshipsMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ScholarshipsMySuffixState = initialState, action): ScholarshipsMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SCHOLARSHIPS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SCHOLARSHIPS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SCHOLARSHIPS):
    case REQUEST(ACTION_TYPES.UPDATE_SCHOLARSHIPS):
    case REQUEST(ACTION_TYPES.DELETE_SCHOLARSHIPS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SCHOLARSHIPS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SCHOLARSHIPS):
    case FAILURE(ACTION_TYPES.CREATE_SCHOLARSHIPS):
    case FAILURE(ACTION_TYPES.UPDATE_SCHOLARSHIPS):
    case FAILURE(ACTION_TYPES.DELETE_SCHOLARSHIPS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SCHOLARSHIPS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SCHOLARSHIPS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SCHOLARSHIPS):
    case SUCCESS(ACTION_TYPES.UPDATE_SCHOLARSHIPS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SCHOLARSHIPS):
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

const apiUrl = 'api/scholarships';

// Actions

export const getEntities: ICrudGetAllAction<IScholarshipsMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SCHOLARSHIPS_LIST,
  payload: axios.get<IScholarshipsMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IScholarshipsMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SCHOLARSHIPS,
    payload: axios.get<IScholarshipsMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IScholarshipsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SCHOLARSHIPS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IScholarshipsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SCHOLARSHIPS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IScholarshipsMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SCHOLARSHIPS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
