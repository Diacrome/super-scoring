import axios from "axios";
import { TestId } from "../types/testDescription";

export const fetchTestDecription = async (testId: TestId) => {
  try {
    const response = await axios.get(
      `http://localhost:8000/test/info/${testId}`
    );
    return response.data;
  } catch (e) {
    console.error(e);
  }
};
