import { StatusAction, StatusActionType } from "../../types/status";
import { Dispatch } from "redux";
import axios from "axios";

export const fetchStatus = () => {
  return async (dispatch: Dispatch<StatusAction>) => {
    try {
      dispatch({ type: StatusActionType.FetchStatus });
      const response = await axios.get("http://localhost:8000/test/status");
      dispatch({
        type: StatusActionType.FetchStatusSuccess,
        payload: response.data,
      });
    } catch (e) {
      console.error(e);
    }
  };
};
