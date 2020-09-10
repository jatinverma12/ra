import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICoursesMySuffix, defaultValue } from 'app/shared/model/courses-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_COURSES_LIST: 'courses/FETCH_COURSES_LIST',
  FETCH_COURSES: 'courses/FETCH_COURSES',
  CREATE_COURSES: 'courses/CREATE_COURSES',
  UPDATE_COURSES: 'courses/UPDATE_COURSES',
  DELETE_COURSES: 'courses/DELETE_COURSES',
  RESET: 'courses/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICoursesMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CoursesMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: CoursesMySuffixState = initialState, action): CoursesMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COURSES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COURSES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COURSES):
    case REQUEST(ACTION_TYPES.UPDATE_COURSES):
    case REQUEST(ACTION_TYPES.DELETE_COURSES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_COURSES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COURSES):
    case FAILURE(ACTION_TYPES.CREATE_COURSES):
    case FAILURE(ACTION_TYPES.UPDATE_COURSES):
    case FAILURE(ACTION_TYPES.DELETE_COURSES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_COURSES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_COURSES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COURSES):
    case SUCCESS(ACTION_TYPES.UPDATE_COURSES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COURSES):
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

const apiUrl = 'api/courses';

// Actions

export const getEntities: ICrudGetAllAction<ICoursesMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COURSES_LIST,
  payload: axios.get<ICoursesMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICoursesMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COURSES,
    payload: axios.get<ICoursesMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICoursesMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COURSES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICoursesMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COURSES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICoursesMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COURSES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
