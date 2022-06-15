import {
  AnswerType,
  QuestionAnswers,
  SelectedOption,
} from "../types/questions";

export const getSelectedOptionInititialState = (
  answerType: AnswerType,
  questionAnswers: QuestionAnswers
): SelectedOption => {
  if (
    answerType === AnswerType.SingleChoice ||
    answerType === AnswerType.Ranking
  ) {
    return [0];
  }
  return Array(questionAnswers.length).fill(0);
};
