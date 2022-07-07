import axios from "axios";
import { backendLocation } from "../types/locations";

export const fetchData = async (path: string) => {
  try {
    const url = `${backendLocation}/${path}`;
    const response = await axios.get(url);
    return response.data;
  } catch (e) {
    console.error(e);
  }
};
