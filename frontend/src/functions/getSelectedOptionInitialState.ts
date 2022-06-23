import {
  AnswerType,
  QuestionAnswers,
  SelectedOption,
} from "../types/questions";

export const getSelectedOptionInitialState = (
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
