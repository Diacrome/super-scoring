import axios from "axios";

export const updateDefaultToken = () => {
  axios.defaults.headers.common["Authorization"] =
    localStorage.getItem("Authorization") || "";
};
