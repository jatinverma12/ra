import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IStudentsSubjectsMySuffix, defaultValue } from 'app/shared/model/students-subjects-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_STUDENTSSUBJECTS_LIST: 'studentsSubjects/FETCH_STUDENTSSUBJECTS_LIST',
  FETCH_STUDENTSSUBJECTS: 'studentsSubjects/FETCH_STUDENTSSUBJECTS',
  CREATE_STUDENTSSUBJECTS: 'studentsSubjects/CREATE_STUDENTSSUBJECTS',
  UPDATE_STUDENTSSUBJECTS: 'studentsSubjects/UPDATE_STUDENTSSUBJECTS',
  DELETE_STUDENTSSUBJECTS: 'studentsSubjects/DELETE_STUDENTSSUBJECTS',
  RESET: 'studentsSubjects/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IStudentsSubjectsMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type StudentsSubjectsMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: StudentsSubjectsMySuffixState = initialState, action): StudentsSubjectsMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_STUDENTSSUBJECTS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_STUDENTSSUBJECTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_STUDENTSSUBJECTS):
    case REQUEST(ACTION_TYPES.UPDATE_STUDENTSSUBJECTS):
    case REQUEST(ACTION_TYPES.DELETE_STUDENTSSUBJECTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_STUDENTSSUBJECTS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_STUDENTSSUBJECTS):
    case FAILURE(ACTION_TYPES.CREATE_STUDENTSSUBJECTS):
    case FAILURE(ACTION_TYPES.UPDATE_STUDENTSSUBJECTS):
    case FAILURE(ACTION_TYPES.DELETE_STUDENTSSUBJECTS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_STUDENTSSUBJECTS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_STUDENTSSUBJECTS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_STUDENTSSUBJECTS):
    case SUCCESS(ACTION_TYPES.UPDATE_STUDENTSSUBJECTS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_STUDENTSSUBJECTS):
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

const apiUrl = 'api/students-subjects';

// Actions

export const getEntities: ICrudGetAllAction<IStudentsSubjectsMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_STUDENTSSUBJECTS_LIST,
  payload: axios.get<IStudentsSubjectsMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IStudentsSubjectsMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_STUDENTSSUBJECTS,
    payload: axios.get<IStudentsSubjectsMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IStudentsSubjectsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_STUDENTSSUBJECTS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IStudentsSubjectsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_STUDENTSSUBJECTS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IStudentsSubjectsMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_STUDENTSSUBJECTS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
