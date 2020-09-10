import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAcademicSessionsMySuffix, defaultValue } from 'app/shared/model/academic-sessions-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_ACADEMICSESSIONS_LIST: 'academicSessions/FETCH_ACADEMICSESSIONS_LIST',
  FETCH_ACADEMICSESSIONS: 'academicSessions/FETCH_ACADEMICSESSIONS',
  CREATE_ACADEMICSESSIONS: 'academicSessions/CREATE_ACADEMICSESSIONS',
  UPDATE_ACADEMICSESSIONS: 'academicSessions/UPDATE_ACADEMICSESSIONS',
  DELETE_ACADEMICSESSIONS: 'academicSessions/DELETE_ACADEMICSESSIONS',
  RESET: 'academicSessions/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAcademicSessionsMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type AcademicSessionsMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: AcademicSessionsMySuffixState = initialState, action): AcademicSessionsMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ACADEMICSESSIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ACADEMICSESSIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ACADEMICSESSIONS):
    case REQUEST(ACTION_TYPES.UPDATE_ACADEMICSESSIONS):
    case REQUEST(ACTION_TYPES.DELETE_ACADEMICSESSIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ACADEMICSESSIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ACADEMICSESSIONS):
    case FAILURE(ACTION_TYPES.CREATE_ACADEMICSESSIONS):
    case FAILURE(ACTION_TYPES.UPDATE_ACADEMICSESSIONS):
    case FAILURE(ACTION_TYPES.DELETE_ACADEMICSESSIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACADEMICSESSIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ACADEMICSESSIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ACADEMICSESSIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_ACADEMICSESSIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ACADEMICSESSIONS):
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

const apiUrl = 'api/academic-sessions';

// Actions

export const getEntities: ICrudGetAllAction<IAcademicSessionsMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ACADEMICSESSIONS_LIST,
  payload: axios.get<IAcademicSessionsMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IAcademicSessionsMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ACADEMICSESSIONS,
    payload: axios.get<IAcademicSessionsMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAcademicSessionsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ACADEMICSESSIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAcademicSessionsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ACADEMICSESSIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAcademicSessionsMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ACADEMICSESSIONS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
