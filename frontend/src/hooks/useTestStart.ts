import axios from "axios";
import { useAppDispatch } from "./useAppDispatch";
import { fetchStatus } from "../store/action-creators/status";
import { TestId } from "../types/testDescription";

export const useTestStart = () => {
  const dispatch = useAppDispatch();

  return async (testId: TestId) => {
    await axios
      .post(`http://localhost:8000/start/${testId}`)
      .then(() => dispatch(fetchStatus()))
      .catch((e) => console.error(e));
  };
};
