import React, { FC } from "react";
import {
  HandleOptionChangeEvent,
  MultipleQuestionFormProps,
} from "../types/questions";
import QuestionFormWithPlaceholders from "./QuestionFormWithPlaceholders";

export const MultipleQuestionsSingleChoiceForm: FC<
  MultipleQuestionFormProps
> = ({ questionText, questionAnswers, selectedOption, setSelectedOption }) => {
  const handleOptionChange = (e: HandleOptionChangeEvent) => {
    setSelectedOption(
      selectedOption.map((option, value) =>
        value === +e.target.name ? +e.target.value : option
      )
    );
  };

  return (
    <QuestionFormWithPlaceholders
      questionText={questionText}
      questionAnswers={questionAnswers}
      handleOptionChange={handleOptionChange}
    />
  );
};

export default MultipleQuestionsSingleChoiceForm;
