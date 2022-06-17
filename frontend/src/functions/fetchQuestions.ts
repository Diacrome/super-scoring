import axios from "axios";

export const fetchQuestions = async () => {
  try {
    const response = await axios.get(`http://localhost:8000/questions`);
    return response.data;
  } catch (e) {
    console.error(e);
  }
};
