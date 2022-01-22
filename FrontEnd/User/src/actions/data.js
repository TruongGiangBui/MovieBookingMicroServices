import {ActionTypes} from '../core/constants';

export function setData(data) {
  return {
    type: ActionTypes.DATA,
    data
  };
};