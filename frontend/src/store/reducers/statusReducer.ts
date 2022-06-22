import {
  StatusAction,
  StatusActionType,
  StatusState,
} from "../../types/status";

const initialState: StatusState = {
  loading: true,
  authorized: false,
  role: null,
  currentPass: null,
};

export const statusReducer = (
  state = initialState,
  action: StatusAction
): StatusState => {
  switch (action.type) {
    case StatusActionType.FetchStatus:
      return { ...state, loading: true };
    case StatusActionType.FetchStatusSuccess:
      return {
        ...state,
        loading: false,
        role: action.payload.role,
        authorized: action.payload.authorized,
        currentPass: action.payload.currentPass,
      };
    default:
      return state;
  }
};
