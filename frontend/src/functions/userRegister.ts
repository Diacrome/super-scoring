import { RegisterParams } from "../types/login";
import axios from "axios";
import { backendLocation } from "../types/locations";

export const userRegister = async (registerParams: RegisterParams) => {
  const params = new URLSearchParams({ ...registerParams });
  axios
    .post(`${backendLocation}/auth/register`, params)
    .catch((e) => console.error(e));
};
