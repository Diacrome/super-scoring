import axios from "axios";

export const setDefaultToken = () => {
  axios.defaults.headers.common["Authorization"] =
    localStorage.getItem("Authorization") || "";
};
