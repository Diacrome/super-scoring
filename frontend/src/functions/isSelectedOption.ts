import { AnswerType, SelectedOption } from "../types/questions";

export const isSelectedOption = (
  selectedOption: SelectedOption,
  answerType: AnswerType
) => {
  if (
    answerType === AnswerType.SingleChoice ||
    answerType === AnswerType.MultipleQuestionsSingleChoice
  ) {
    return !selectedOption.includes(0);
  } else if (answerType === AnswerType.MultipleChoice) {
    return selectedOption.includes(1);
  }
  return true;
};
