import { StatusAction, StatusActionType } from "../../types/status";
import { Dispatch } from "redux";
import axios from "axios";
import { backendLocation } from "../../types/locations";

export const fetchStatus = () => {
  return async (dispatch: Dispatch<StatusAction>) => {
    try {
      dispatch({ type: StatusActionType.FetchStatus });
      const response = await axios.get(`${backendLocation}/test/status`);
      dispatch({
        type: StatusActionType.FetchStatusSuccess,
        payload: response.data,
      });
    } catch (e) {
      console.error(e);
    }
  };
};
