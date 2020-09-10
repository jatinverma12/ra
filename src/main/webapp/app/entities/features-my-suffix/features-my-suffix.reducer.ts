import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFeaturesMySuffix, defaultValue } from 'app/shared/model/features-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_FEATURES_LIST: 'features/FETCH_FEATURES_LIST',
  FETCH_FEATURES: 'features/FETCH_FEATURES',
  CREATE_FEATURES: 'features/CREATE_FEATURES',
  UPDATE_FEATURES: 'features/UPDATE_FEATURES',
  DELETE_FEATURES: 'features/DELETE_FEATURES',
  RESET: 'features/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFeaturesMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FeaturesMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: FeaturesMySuffixState = initialState, action): FeaturesMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FEATURES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FEATURES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FEATURES):
    case REQUEST(ACTION_TYPES.UPDATE_FEATURES):
    case REQUEST(ACTION_TYPES.DELETE_FEATURES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FEATURES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FEATURES):
    case FAILURE(ACTION_TYPES.CREATE_FEATURES):
    case FAILURE(ACTION_TYPES.UPDATE_FEATURES):
    case FAILURE(ACTION_TYPES.DELETE_FEATURES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FEATURES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FEATURES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FEATURES):
    case SUCCESS(ACTION_TYPES.UPDATE_FEATURES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FEATURES):
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

const apiUrl = 'api/features';

// Actions

export const getEntities: ICrudGetAllAction<IFeaturesMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FEATURES_LIST,
  payload: axios.get<IFeaturesMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFeaturesMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FEATURES,
    payload: axios.get<IFeaturesMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFeaturesMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FEATURES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFeaturesMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FEATURES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFeaturesMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FEATURES,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
