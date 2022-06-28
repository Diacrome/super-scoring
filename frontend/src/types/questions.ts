import { ChangeEventHandler } from "react";

export const ANSWER_PLH = /%answer\d*/;

export enum AnswerType {
  SingleChoice = "SINGLE_CHOICE",
  MultipleChoice = "MULTIPLE_CHOICE",
  MultipleQuestionsSingleChoice = "MULTIPLE_QUESTIONS_SINGLE_CHOICE",
  Ranking = "RANKING",
}

export enum ContentType {
  Image = "image",
  Video = "video",
}

type QuestionContent = Array<{
  url: string;
  type: ContentType;
}>;

export interface SingleQuestion {
  question: string;
  content: QuestionContent | null;
  payload: Record<string, string>;
  answerType:
    | AnswerType.SingleChoice
    | AnswerType.MultipleChoice
    | AnswerType.Ranking;
}

export interface MultipleQuestion {
  question: string;
  content: QuestionContent | null;
  payload: Record<string, Record<string, string>>;
  answerType: AnswerType.MultipleQuestionsSingleChoice;
}

export type Question = SingleQuestion | MultipleQuestion;
export type Questions = Record<string, Question>;

type QuestionText = string[];
export type SingleQuestionAnswers = string[];
export type MultipleQuestionAnswers = string[][];
export type QuestionAnswers = SingleQuestionAnswers | MultipleQuestionAnswers;
type QuestionOrder = number;
type QuestionCount = number;
export type SelectedOption = number[];
type SetSelectedOption = (state: SelectedOption) => void;

export interface TestAnswerButtonProps {
  questionOrder: QuestionOrder;
  questionsCount: QuestionCount;
  selectedOption: SelectedOption;
  answerType: AnswerType;
}

export interface BasicFormProps {
  questionText: QuestionText;
  questionAnswers: QuestionAnswers;
}

export interface QuestionFormProps extends BasicFormProps {
  questionOrder: QuestionOrder;
  questionCount: QuestionCount;
  answerType: AnswerType;
}

export interface SingleQuestionFormProps extends BasicFormProps {
  selectedOption: SelectedOption;
  setSelectedOption: SetSelectedOption;
  questionAnswers: SingleQuestionAnswers;
}

export interface MultipleQuestionFormProps extends BasicFormProps {
  selectedOption: SelectedOption;
  setSelectedOption: SetSelectedOption;
  questionAnswers: MultipleQuestionAnswers;
}

export interface QuestionFormWithInputsProps extends BasicFormProps {
  questionAnswers: SingleQuestionAnswers;
  selectedOption: SelectedOption;
  handleOptionChange: ChangeEventHandler<HTMLInputElement>;
}

export interface QuestionFormWithPlaceholdersProps extends BasicFormProps {
  questionAnswers: MultipleQuestionAnswers;
  selectedOption: SelectedOption;
  handleOptionChange: ChangeEventHandler<HTMLSelectElement>;
}

export interface QuestionContentProps {
  content: QuestionContent;
}
