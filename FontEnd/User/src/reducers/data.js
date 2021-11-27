import {ActionTypes} from '../core/constants';

const initialState = {token:localStorage.getItem('apptoken')};

export default function(state = initialState, action) {
  switch (action.type) {
    case ActionTypes.DATA:
      return setData(state, action);
  }
  return state;
}

function setData(state, action) {
  const data = action.playload;
  return {...state,token:data};
}