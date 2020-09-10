import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserPreferencesMySuffix, defaultValue } from 'app/shared/model/user-preferences-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_USERPREFERENCES_LIST: 'userPreferences/FETCH_USERPREFERENCES_LIST',
  FETCH_USERPREFERENCES: 'userPreferences/FETCH_USERPREFERENCES',
  CREATE_USERPREFERENCES: 'userPreferences/CREATE_USERPREFERENCES',
  UPDATE_USERPREFERENCES: 'userPreferences/UPDATE_USERPREFERENCES',
  DELETE_USERPREFERENCES: 'userPreferences/DELETE_USERPREFERENCES',
  RESET: 'userPreferences/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserPreferencesMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type UserPreferencesMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: UserPreferencesMySuffixState = initialState, action): UserPreferencesMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERPREFERENCES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERPREFERENCES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_USERPREFERENCES):
    case REQUEST(ACTION_TYPES.UPDATE_USERPREFERENCES):
    case REQUEST(ACTION_TYPES.DELETE_USERPREFERENCES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_USERPREFERENCES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERPREFERENCES):
    case FAILURE(ACTION_TYPES.CREATE_USERPREFERENCES):
    case FAILURE(ACTION_TYPES.UPDATE_USERPREFERENCES):
    case FAILURE(ACTION_TYPES.DELETE_USERPREFERENCES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERPREFERENCES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERPREFERENCES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERPREFERENCES):
    case SUCCESS(ACTION_TYPES.UPDATE_USERPREFERENCES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERPREFERENCES):
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

const apiUrl = 'api/user-preferences';

// Actions

export const getEntities: ICrudGetAllAction<IUserPreferencesMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_USERPREFERENCES_LIST,
  payload: axios.get<IUserPreferencesMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IUserPreferencesMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERPREFERENCES,
    payload: axios.get<IUserPreferencesMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUserPreferencesMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERPREFERENCES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserPreferencesMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERPREFERENCES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserPreferencesMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERPREFERENCES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
