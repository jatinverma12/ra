import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IChaptersMySuffix, defaultValue } from 'app/shared/model/chapters-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_CHAPTERS_LIST: 'chapters/FETCH_CHAPTERS_LIST',
  FETCH_CHAPTERS: 'chapters/FETCH_CHAPTERS',
  CREATE_CHAPTERS: 'chapters/CREATE_CHAPTERS',
  UPDATE_CHAPTERS: 'chapters/UPDATE_CHAPTERS',
  DELETE_CHAPTERS: 'chapters/DELETE_CHAPTERS',
  RESET: 'chapters/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IChaptersMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ChaptersMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ChaptersMySuffixState = initialState, action): ChaptersMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CHAPTERS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CHAPTERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CHAPTERS):
    case REQUEST(ACTION_TYPES.UPDATE_CHAPTERS):
    case REQUEST(ACTION_TYPES.DELETE_CHAPTERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CHAPTERS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CHAPTERS):
    case FAILURE(ACTION_TYPES.CREATE_CHAPTERS):
    case FAILURE(ACTION_TYPES.UPDATE_CHAPTERS):
    case FAILURE(ACTION_TYPES.DELETE_CHAPTERS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHAPTERS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHAPTERS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CHAPTERS):
    case SUCCESS(ACTION_TYPES.UPDATE_CHAPTERS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CHAPTERS):
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

const apiUrl = 'api/chapters';

// Actions

export const getEntities: ICrudGetAllAction<IChaptersMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CHAPTERS_LIST,
  payload: axios.get<IChaptersMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IChaptersMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CHAPTERS,
    payload: axios.get<IChaptersMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IChaptersMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CHAPTERS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IChaptersMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CHAPTERS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IChaptersMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CHAPTERS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
