import { IsRegister, RegisterParams } from "../types/login";
import axios from "axios";
import { backendLocation } from "../types/locations";

export const userRegister = async (
  registerParams: RegisterParams,
  setIsRegistered: (state: IsRegister) => void
) => {
  const params = new URLSearchParams({ ...registerParams });
  axios
    .post(`${backendLocation}/auth/register`, params)
    .then(() => setIsRegistered(IsRegister.Yes))
    .catch((e) => {
      console.error(e);
      setIsRegistered(IsRegister.Bad);
    });
};
