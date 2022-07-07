import axios, { AxiosError } from "axios";
import { useAppDispatch } from "./useAppDispatch";
import { fetchStatus } from "../store/action-creators/status";
import { TestId } from "../types/testDescription";
import { backendLocation } from "../types/locations";
import { AttemptsInfo } from "../types/attempts";

export const useTestStart = () => {
  const dispatch = useAppDispatch();

  return async (testId: TestId) => {
    try {
      await axios.post(`${backendLocation}/start/${testId}`);
      await dispatch(fetchStatus());
    } catch (e) {
      console.error(e);
      const axiosError = e as AxiosError<AttemptsInfo>;
      if (axiosError.response?.data) {
        return axiosError.response.data;
      }
    }
    return null;
  };
};
