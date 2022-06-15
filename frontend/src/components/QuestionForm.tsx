import React, { FC, useState } from "react";
import {
  AnswerType,
  MultipleQuestionAnswers,
  QuestionFormProps,
  SelectedOption,
  SingleQuestionAnswers,
} from "../types/questions";
import SingleChoiceForm from "./SingleChoiceForm";
import MultipleChoiceForm from "./MultipleChoiceForm";
import MultipleQuestionsSingleChoiceForm from "./MultipleQuestionsSingleChoiceForm";
import TestAnswerButton from "./TestAnswerButton";
import { getSelectedOptionInititialState } from "../functions/getSelectedOptionInitialState";

const QuestionForm: FC<QuestionFormProps> = ({
  questionText,
  questionAnswers,
  answerType,
  questionOrder,
  questionCount,
}) => {
  const [selectedOption, setSelectedOption] = useState<SelectedOption>(
    getSelectedOptionInititialState(answerType, questionAnswers)
  );

  const changeSelectedOption = (state: SelectedOption) => {
    setSelectedOption(state);
  };
  let form = <></>;
  switch (answerType) {
    case AnswerType.SingleChoice:
      form = (
        <SingleChoiceForm
          questionText={questionText}
          questionAnswers={questionAnswers as SingleQuestionAnswers}
          selectedOption={selectedOption}
          setSelectedOption={changeSelectedOption}
        />
      );
      break;
    case AnswerType.MultipleChoice:
      form = (
        <MultipleChoiceForm
          questionText={questionText}
          questionAnswers={questionAnswers as SingleQuestionAnswers}
          selectedOption={selectedOption}
          setSelectedOption={changeSelectedOption}
        />
      );
      break;
    case AnswerType.MultipleQuestionsSingleChoice:
      form = (
        <MultipleQuestionsSingleChoiceForm
          questionText={questionText}
          selectedOption={selectedOption}
          questionAnswers={questionAnswers as MultipleQuestionAnswers}
          setSelectedOption={changeSelectedOption}
        />
      );
      break;
    case AnswerType.Ranking:
      break;
  }
  return (
    <>
      {form}
      <TestAnswerButton
        questionOrder={questionOrder}
        questionsCount={questionCount}
        selectedOption={selectedOption}
        answerType={answerType}
      />
    </>
  );
};

export default QuestionForm;
