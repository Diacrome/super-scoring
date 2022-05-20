import {
  CurrentPass,
  StatusAction,
  StatusActionType,
} from "../../types/status";
import { Dispatch } from "redux";

export const fetchStatus = () => {
  return async (dispatch: Dispatch<StatusAction>) => {
    dispatch({ type: StatusActionType.FetchStatus });
    const authorized = localStorage.getItem("authorized") == "true";
    const currentPassLocalStorage = localStorage.getItem("currentPass");
    const currentPass: CurrentPass =
      currentPassLocalStorage && JSON.parse(currentPassLocalStorage);
    const statusPayload = {
      authorized,
      currentPass,
    };
    setTimeout(
      () =>
        dispatch({
          type: StatusActionType.FetchStatusSuccess,
          payload: statusPayload,
        }),
      500
    );
  };
};

export const signIn = () => {
  return { type: StatusActionType.SignIn };
};

export const logOut = () => {
  return { type: StatusActionType.LogOut };
};

export const statusPass: CurrentPass = {
  answeredQuestions: {
    1: false,
    2: false,
    3: false,
    4: false,
    5: false,
    6: false,
    7: false,
    8: false,
    9: false,
    10: false,
  },
  startTime: 123,
  testId: 1,
};
