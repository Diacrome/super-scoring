import axios from "axios";
import { useAppDispatch } from "./useAppDispatch";
import { fetchStatus } from "../store/action-creators/status";
import { TestId } from "../types/testDescription";
import { backendLocation } from "../types/locations";

export const useTestStart = () => {
  const dispatch = useAppDispatch();

  return async (testId: TestId) => {
    await axios
      .post(`${backendLocation}/start/${testId}`)
      .then(() => dispatch(fetchStatus()))
      .catch((e) => console.error(e));
  };
};
