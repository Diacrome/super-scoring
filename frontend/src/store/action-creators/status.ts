import {
  CurrentPass,
  StatusAction,
  StatusActionType,
} from "../../types/status";
import { Dispatch } from "redux";
import axios from "axios";

export const fetchStatus = () => {
  return async (dispatch: Dispatch<StatusAction>) => {
    try {
      const token = localStorage.getItem("Authorization") || "";
      dispatch({ type: StatusActionType.FetchStatus });
      const response = await axios.get("http://localhost:8000/test/status", {
        headers: {
          Authorization: token,
        },
      });
      dispatch({
        type: StatusActionType.FetchStatusSuccess,
        payload: response.data,
      });
    } catch (e) {
      console.error(e);
    }
  };
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
