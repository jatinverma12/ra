import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRoleAccessMySuffix, defaultValue } from 'app/shared/model/role-access-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_ROLEACCESS_LIST: 'roleAccess/FETCH_ROLEACCESS_LIST',
  FETCH_ROLEACCESS: 'roleAccess/FETCH_ROLEACCESS',
  CREATE_ROLEACCESS: 'roleAccess/CREATE_ROLEACCESS',
  UPDATE_ROLEACCESS: 'roleAccess/UPDATE_ROLEACCESS',
  DELETE_ROLEACCESS: 'roleAccess/DELETE_ROLEACCESS',
  RESET: 'roleAccess/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRoleAccessMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RoleAccessMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: RoleAccessMySuffixState = initialState, action): RoleAccessMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ROLEACCESS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ROLEACCESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ROLEACCESS):
    case REQUEST(ACTION_TYPES.UPDATE_ROLEACCESS):
    case REQUEST(ACTION_TYPES.DELETE_ROLEACCESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ROLEACCESS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ROLEACCESS):
    case FAILURE(ACTION_TYPES.CREATE_ROLEACCESS):
    case FAILURE(ACTION_TYPES.UPDATE_ROLEACCESS):
    case FAILURE(ACTION_TYPES.DELETE_ROLEACCESS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ROLEACCESS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ROLEACCESS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ROLEACCESS):
    case SUCCESS(ACTION_TYPES.UPDATE_ROLEACCESS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ROLEACCESS):
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

const apiUrl = 'api/role-accesses';

// Actions

export const getEntities: ICrudGetAllAction<IRoleAccessMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ROLEACCESS_LIST,
  payload: axios.get<IRoleAccessMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRoleAccessMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ROLEACCESS,
    payload: axios.get<IRoleAccessMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRoleAccessMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ROLEACCESS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRoleAccessMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ROLEACCESS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRoleAccessMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ROLEACCESS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
