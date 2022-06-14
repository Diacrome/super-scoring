import {
  StatusAction,
  StatusActionType,
  StatusState,
} from "../../types/status";

const authLocalStorage = Boolean(localStorage.getItem("authorized"));

const initialState: StatusState = {
  loading: true,
  authorized: authLocalStorage,
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
        authorized: action.payload.authorized,
        currentPass: action.payload.currentPass,
      };
    default:
      return state;
  }
};
