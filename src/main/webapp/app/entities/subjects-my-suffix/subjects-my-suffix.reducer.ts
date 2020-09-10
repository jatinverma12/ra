import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISubjectsMySuffix, defaultValue } from 'app/shared/model/subjects-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_SUBJECTS_LIST: 'subjects/FETCH_SUBJECTS_LIST',
  FETCH_SUBJECTS: 'subjects/FETCH_SUBJECTS',
  CREATE_SUBJECTS: 'subjects/CREATE_SUBJECTS',
  UPDATE_SUBJECTS: 'subjects/UPDATE_SUBJECTS',
  DELETE_SUBJECTS: 'subjects/DELETE_SUBJECTS',
  RESET: 'subjects/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISubjectsMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SubjectsMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: SubjectsMySuffixState = initialState, action): SubjectsMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SUBJECTS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUBJECTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SUBJECTS):
    case REQUEST(ACTION_TYPES.UPDATE_SUBJECTS):
    case REQUEST(ACTION_TYPES.DELETE_SUBJECTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SUBJECTS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUBJECTS):
    case FAILURE(ACTION_TYPES.CREATE_SUBJECTS):
    case FAILURE(ACTION_TYPES.UPDATE_SUBJECTS):
    case FAILURE(ACTION_TYPES.DELETE_SUBJECTS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBJECTS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUBJECTS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUBJECTS):
    case SUCCESS(ACTION_TYPES.UPDATE_SUBJECTS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUBJECTS):
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

const apiUrl = 'api/subjects';

// Actions

export const getEntities: ICrudGetAllAction<ISubjectsMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SUBJECTS_LIST,
  payload: axios.get<ISubjectsMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ISubjectsMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUBJECTS,
    payload: axios.get<ISubjectsMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISubjectsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUBJECTS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISubjectsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUBJECTS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISubjectsMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUBJECTS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
