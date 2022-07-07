import { LoginParams } from "../types/login";
import axios from "axios";
import { backendLocation } from "../types/locations";

export const userRegister = async (loginParams: LoginParams) => {
  const params = new URLSearchParams({ ...loginParams, name: "Alex" });
  axios
    .post(`${backendLocation}/auth/register`, params)
    .then((response) => {
      console.log(response);
    })
    .catch((e) => console.error(e));
};
