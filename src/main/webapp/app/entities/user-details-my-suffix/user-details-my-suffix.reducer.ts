import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserDetailsMySuffix, defaultValue } from 'app/shared/model/user-details-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_USERDETAILS_LIST: 'userDetails/FETCH_USERDETAILS_LIST',
  FETCH_USERDETAILS: 'userDetails/FETCH_USERDETAILS',
  CREATE_USERDETAILS: 'userDetails/CREATE_USERDETAILS',
  UPDATE_USERDETAILS: 'userDetails/UPDATE_USERDETAILS',
  DELETE_USERDETAILS: 'userDetails/DELETE_USERDETAILS',
  RESET: 'userDetails/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserDetailsMySuffix>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type UserDetailsMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: UserDetailsMySuffixState = initialState, action): UserDetailsMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERDETAILS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_USERDETAILS):
    case REQUEST(ACTION_TYPES.UPDATE_USERDETAILS):
    case REQUEST(ACTION_TYPES.DELETE_USERDETAILS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_USERDETAILS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERDETAILS):
    case FAILURE(ACTION_TYPES.CREATE_USERDETAILS):
    case FAILURE(ACTION_TYPES.UPDATE_USERDETAILS):
    case FAILURE(ACTION_TYPES.DELETE_USERDETAILS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERDETAILS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERDETAILS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERDETAILS):
    case SUCCESS(ACTION_TYPES.UPDATE_USERDETAILS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERDETAILS):
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

const apiUrl = 'api/user-details';

// Actions

export const getEntities: ICrudGetAllAction<IUserDetailsMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_USERDETAILS_LIST,
    payload: axios.get<IUserDetailsMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUserDetailsMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERDETAILS,
    payload: axios.get<IUserDetailsMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUserDetailsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERDETAILS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserDetailsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERDETAILS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserDetailsMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERDETAILS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
