import axios from "axios";
import { LoginParams } from "../types/login";
import { useAppDispatch } from "./useAppDispatch";
import { fetchStatus } from "../store/action-creators/status";

export const useFetchToken = () => {
  const dispatch = useAppDispatch();

  return async (loginParams: LoginParams) => {
    const params = new URLSearchParams({ ...loginParams });
    axios
      .post("http://localhost:8000/auth/token", params)
      .then((response) => {
        localStorage.setItem("Authorization", response.data.token);
        dispatch(fetchStatus());
      })
      .catch((e) => console.error(e));
  };
};
