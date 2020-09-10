import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITeachersShareMySuffix, defaultValue } from 'app/shared/model/teachers-share-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_TEACHERSSHARE_LIST: 'teachersShare/FETCH_TEACHERSSHARE_LIST',
  FETCH_TEACHERSSHARE: 'teachersShare/FETCH_TEACHERSSHARE',
  CREATE_TEACHERSSHARE: 'teachersShare/CREATE_TEACHERSSHARE',
  UPDATE_TEACHERSSHARE: 'teachersShare/UPDATE_TEACHERSSHARE',
  DELETE_TEACHERSSHARE: 'teachersShare/DELETE_TEACHERSSHARE',
  RESET: 'teachersShare/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITeachersShareMySuffix>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TeachersShareMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: TeachersShareMySuffixState = initialState, action): TeachersShareMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TEACHERSSHARE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TEACHERSSHARE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TEACHERSSHARE):
    case REQUEST(ACTION_TYPES.UPDATE_TEACHERSSHARE):
    case REQUEST(ACTION_TYPES.DELETE_TEACHERSSHARE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TEACHERSSHARE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TEACHERSSHARE):
    case FAILURE(ACTION_TYPES.CREATE_TEACHERSSHARE):
    case FAILURE(ACTION_TYPES.UPDATE_TEACHERSSHARE):
    case FAILURE(ACTION_TYPES.DELETE_TEACHERSSHARE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEACHERSSHARE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEACHERSSHARE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TEACHERSSHARE):
    case SUCCESS(ACTION_TYPES.UPDATE_TEACHERSSHARE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TEACHERSSHARE):
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

const apiUrl = 'api/teachers-shares';

// Actions

export const getEntities: ICrudGetAllAction<ITeachersShareMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TEACHERSSHARE_LIST,
    payload: axios.get<ITeachersShareMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITeachersShareMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TEACHERSSHARE,
    payload: axios.get<ITeachersShareMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITeachersShareMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TEACHERSSHARE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITeachersShareMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TEACHERSSHARE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITeachersShareMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TEACHERSSHARE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
