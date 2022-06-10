import axios from "axios";
import { useAppDispatch } from "./useAppDispatch";
import { fetchStatus } from "../store/action-creators/status";

export const useSendAnswer = () => {
  const dispatch = useAppDispatch();

  return async (questionOrder: string, answer: string) => {
    answer = JSON.stringify({ answer });
    const params = new URLSearchParams({ questionOrder, answer });
    axios
      .post("http://localhost:8000/answer", params)
      .then(() => {
        dispatch(fetchStatus());
      })
      .catch((e) => console.error(e));
  };
};
