import {
  StatusAction,
  StatusActionType,
  StatusState,
} from "../../types/status";

const authLocalStorage = Boolean(localStorage.getItem("authorized"));

const initialState: StatusState = {
  loading: true,
  authorized: authLocalStorage,
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
    case StatusActionType.SignIn:
      return { ...state, authorized: true };
    case StatusActionType.LogOut:
      return { ...state, authorized: false };
    default:
      return state;
  }
};
