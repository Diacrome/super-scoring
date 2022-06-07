import { LoginParams } from "../types/login";
import axios from "axios";

export const userRegister = async (loginParams: LoginParams) => {
  const params = new URLSearchParams({ ...loginParams, name: "Alex" });
  params.append("name", "Alex");
  axios
    .post("http://localhost:8000/auth/register", params)
    .then((response) => {
      console.log(response);
    })
    .catch((e) => console.error(e));
};
