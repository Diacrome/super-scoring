import axios from "axios";

export const fetchData = async (url: string) => {
  try {
    const response = await axios.get(url);
    return response.data;
  } catch (e) {
    console.error(e);
  }
};
