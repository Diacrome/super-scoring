import { useAppDispatch } from "./useAppDispatch";
import { fetchStatus } from "../store/action-creators/status";
import axios from "axios";

export const useCancelPass = () => {
  const dispatch = useAppDispatch();
  return async () => {
    axios
      .post("http://localhost:8000/cancel")
      .then(() => dispatch(fetchStatus()))
      .catch((e) => console.error(e));
  };
};
