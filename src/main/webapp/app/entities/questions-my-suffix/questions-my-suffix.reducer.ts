import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IQuestionsMySuffix, defaultValue } from 'app/shared/model/questions-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_QUESTIONS_LIST: 'questions/FETCH_QUESTIONS_LIST',
  FETCH_QUESTIONS: 'questions/FETCH_QUESTIONS',
  CREATE_QUESTIONS: 'questions/CREATE_QUESTIONS',
  UPDATE_QUESTIONS: 'questions/UPDATE_QUESTIONS',
  DELETE_QUESTIONS: 'questions/DELETE_QUESTIONS',
  SET_BLOB: 'questions/SET_BLOB',
  RESET: 'questions/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IQuestionsMySuffix>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type QuestionsMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: QuestionsMySuffixState = initialState, action): QuestionsMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_QUESTIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_QUESTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_QUESTIONS):
    case REQUEST(ACTION_TYPES.UPDATE_QUESTIONS):
    case REQUEST(ACTION_TYPES.DELETE_QUESTIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_QUESTIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_QUESTIONS):
    case FAILURE(ACTION_TYPES.CREATE_QUESTIONS):
    case FAILURE(ACTION_TYPES.UPDATE_QUESTIONS):
    case FAILURE(ACTION_TYPES.DELETE_QUESTIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_QUESTIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_QUESTIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_QUESTIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_QUESTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_QUESTIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/questions';

// Actions

export const getEntities: ICrudGetAllAction<IQuestionsMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_QUESTIONS_LIST,
    payload: axios.get<IQuestionsMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IQuestionsMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_QUESTIONS,
    payload: axios.get<IQuestionsMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IQuestionsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_QUESTIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IQuestionsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_QUESTIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IQuestionsMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_QUESTIONS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
