import axios from "axios";
import { useAppDispatch } from "./useAppDispatch";
import { fetchStatus } from "../store/action-creators/status";
import { buildAnswer } from "../functions/buildAnswer";
import { AnswerType, SelectedOption } from "../types/questions";
import { backendLocation } from "../types/locations";

export const useSendAnswer = () => {
  const dispatch = useAppDispatch();

  return async (
    questionOrder: number,
    selectedOption: SelectedOption,
    answerType: AnswerType
  ) => {
    let answer = buildAnswer(selectedOption, answerType);
    const params = new URLSearchParams({
      questionOrder: `${questionOrder}`,
      answer,
    });
    axios
      .post(`${backendLocation}/answer`, params)
      .then(() => {
        dispatch(fetchStatus());
      })
      .catch((e) => console.error(e));
  };
};
