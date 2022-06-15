import { AnswerType, SelectedOption } from "../types/questions";

export const buildAnswer = (
  selectedOption: SelectedOption,
  answerType: AnswerType
) => {
  let answer: Record<string, string> = {};
  switch (answerType) {
    case AnswerType.SingleChoice:
      answer = { answer: selectedOption[0] + "" };
      break;
    case AnswerType.MultipleChoice:
      let answerNum = 1;
      selectedOption.forEach((option, number) => {
        if (option) {
          answer[`multiple_answer${answerNum++}`] = number + 1 + "";
        }
      });
      break;
    case AnswerType.MultipleQuestionsSingleChoice:
      selectedOption.forEach((option, number) => {
        answer[`answer${number + 1}`] = option + "";
      });
      break;
    case AnswerType.Ranking:
      answer = { "1": "1", "2": "2", "3": "3", "4": "4" };
  }
  return JSON.stringify(answer);
};
