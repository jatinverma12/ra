import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITeachersMySuffix, defaultValue } from 'app/shared/model/teachers-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_TEACHERS_LIST: 'teachers/FETCH_TEACHERS_LIST',
  FETCH_TEACHERS: 'teachers/FETCH_TEACHERS',
  CREATE_TEACHERS: 'teachers/CREATE_TEACHERS',
  UPDATE_TEACHERS: 'teachers/UPDATE_TEACHERS',
  DELETE_TEACHERS: 'teachers/DELETE_TEACHERS',
  RESET: 'teachers/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITeachersMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TeachersMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: TeachersMySuffixState = initialState, action): TeachersMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TEACHERS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TEACHERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TEACHERS):
    case REQUEST(ACTION_TYPES.UPDATE_TEACHERS):
    case REQUEST(ACTION_TYPES.DELETE_TEACHERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TEACHERS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TEACHERS):
    case FAILURE(ACTION_TYPES.CREATE_TEACHERS):
    case FAILURE(ACTION_TYPES.UPDATE_TEACHERS):
    case FAILURE(ACTION_TYPES.DELETE_TEACHERS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEACHERS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEACHERS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TEACHERS):
    case SUCCESS(ACTION_TYPES.UPDATE_TEACHERS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TEACHERS):
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

const apiUrl = 'api/teachers';

// Actions

export const getEntities: ICrudGetAllAction<ITeachersMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TEACHERS_LIST,
  payload: axios.get<ITeachersMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITeachersMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TEACHERS,
    payload: axios.get<ITeachersMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITeachersMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TEACHERS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITeachersMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TEACHERS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITeachersMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TEACHERS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
