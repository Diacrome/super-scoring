import { useAppDispatch } from "./useAppDispatch";
import { fetchStatus } from "../store/action-creators/status";
import axios from "axios";
import { backendLocation } from "../types/locations";

export const useCancelPass = () => {
  const dispatch = useAppDispatch();
  return async () => {
    axios
      .post(`${backendLocation}/cancel`)
      .then(() => dispatch(fetchStatus()))
      .catch((e) => console.error(e));
  };
};
