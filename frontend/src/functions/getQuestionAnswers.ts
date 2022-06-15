import { AnswerType, Question, QuestionAnswers } from "../types/questions";

export const getQuestionAnswers = (
  answerType: AnswerType,
  question: Question
): QuestionAnswers => {
  if (
    answerType === AnswerType.SingleChoice ||
    answerType === AnswerType.MultipleChoice ||
    answerType === AnswerType.Ranking
  ) {
    return Object.values(question.payload);
  }
  return Object.values(question.payload).map((answer) => Object.values(answer));
};
