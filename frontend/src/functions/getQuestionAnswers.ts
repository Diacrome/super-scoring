import { AnswerType, Question, QuestionAnswers } from "../types/questions";

export const getQuestionAnswers = (
  answerType: AnswerType,
  question: Question
): QuestionAnswers => {
  if (
    [
      AnswerType.SingleChoice,
      AnswerType.MultipleChoice,
      AnswerType.Ranking,
    ].includes(answerType)
  ) {
    return Object.values(question.payload);
  }
  return Object.values(question.payload).map((answer) => Object.values(answer));
};
