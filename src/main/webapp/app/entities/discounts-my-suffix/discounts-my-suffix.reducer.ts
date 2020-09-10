import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDiscountsMySuffix, defaultValue } from 'app/shared/model/discounts-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_DISCOUNTS_LIST: 'discounts/FETCH_DISCOUNTS_LIST',
  FETCH_DISCOUNTS: 'discounts/FETCH_DISCOUNTS',
  CREATE_DISCOUNTS: 'discounts/CREATE_DISCOUNTS',
  UPDATE_DISCOUNTS: 'discounts/UPDATE_DISCOUNTS',
  DELETE_DISCOUNTS: 'discounts/DELETE_DISCOUNTS',
  RESET: 'discounts/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDiscountsMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DiscountsMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: DiscountsMySuffixState = initialState, action): DiscountsMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DISCOUNTS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DISCOUNTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DISCOUNTS):
    case REQUEST(ACTION_TYPES.UPDATE_DISCOUNTS):
    case REQUEST(ACTION_TYPES.DELETE_DISCOUNTS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DISCOUNTS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DISCOUNTS):
    case FAILURE(ACTION_TYPES.CREATE_DISCOUNTS):
    case FAILURE(ACTION_TYPES.UPDATE_DISCOUNTS):
    case FAILURE(ACTION_TYPES.DELETE_DISCOUNTS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISCOUNTS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DISCOUNTS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DISCOUNTS):
    case SUCCESS(ACTION_TYPES.UPDATE_DISCOUNTS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DISCOUNTS):
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

const apiUrl = 'api/discounts';

// Actions

export const getEntities: ICrudGetAllAction<IDiscountsMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DISCOUNTS_LIST,
  payload: axios.get<IDiscountsMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IDiscountsMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DISCOUNTS,
    payload: axios.get<IDiscountsMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDiscountsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DISCOUNTS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDiscountsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DISCOUNTS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDiscountsMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DISCOUNTS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
