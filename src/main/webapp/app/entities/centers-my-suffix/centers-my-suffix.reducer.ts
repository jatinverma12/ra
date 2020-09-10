import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICentersMySuffix, defaultValue } from 'app/shared/model/centers-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_CENTERS_LIST: 'centers/FETCH_CENTERS_LIST',
  FETCH_CENTERS: 'centers/FETCH_CENTERS',
  CREATE_CENTERS: 'centers/CREATE_CENTERS',
  UPDATE_CENTERS: 'centers/UPDATE_CENTERS',
  DELETE_CENTERS: 'centers/DELETE_CENTERS',
  RESET: 'centers/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICentersMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type CentersMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: CentersMySuffixState = initialState, action): CentersMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CENTERS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CENTERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CENTERS):
    case REQUEST(ACTION_TYPES.UPDATE_CENTERS):
    case REQUEST(ACTION_TYPES.DELETE_CENTERS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CENTERS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CENTERS):
    case FAILURE(ACTION_TYPES.CREATE_CENTERS):
    case FAILURE(ACTION_TYPES.UPDATE_CENTERS):
    case FAILURE(ACTION_TYPES.DELETE_CENTERS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CENTERS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CENTERS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CENTERS):
    case SUCCESS(ACTION_TYPES.UPDATE_CENTERS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CENTERS):
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

const apiUrl = 'api/centers';

// Actions

export const getEntities: ICrudGetAllAction<ICentersMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CENTERS_LIST,
  payload: axios.get<ICentersMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ICentersMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CENTERS,
    payload: axios.get<ICentersMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICentersMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CENTERS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICentersMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CENTERS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICentersMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CENTERS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
