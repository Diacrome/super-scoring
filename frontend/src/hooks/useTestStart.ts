import axios from "axios";
import { useAppDispatch } from "./useAppDispatch";
import { fetchStatus } from "../store/action-creators/status";
import { TestId } from "../types/testDescription";

export const useTestStart = () => {
  const dispatch = useAppDispatch();

  return async (testId: TestId) => {
    const token = localStorage.getItem("Authorization") || "";
    axios
      .post(
        `http://localhost:8000/start/${testId}`,
        {},
        {
          headers: {
            Authorization: token,
          },
        }
      )
      .then(() => dispatch(fetchStatus()))
      .catch((e) => console.error(e));
  };
};
