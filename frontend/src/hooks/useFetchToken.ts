import axios from "axios";
import { LoginParams } from "../types/login";
import { useAppDispatch } from "./useAppDispatch";
import { fetchStatus } from "../store/action-creators/status";
import { backendLocation } from "../types/locations";

export const useFetchToken = () => {
  const dispatch = useAppDispatch();

  return async (loginParams: LoginParams) => {
    const params = new URLSearchParams({ ...loginParams });
    axios
      .post(`${backendLocation}/auth/token`, params)
      .then((response) => {
        localStorage.setItem("Authorization", response.data.token);
        axios.defaults.headers.common["Authorization"] = response.data.token;
        dispatch(fetchStatus());
      })
      .catch((e) => console.error(e));
  };
};
