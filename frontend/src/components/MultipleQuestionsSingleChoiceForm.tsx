import React, { ChangeEventHandler, FC } from "react";
import { MultipleQuestionFormProps } from "../types/questions";
import QuestionFormWithPlaceholders from "./QuestionFormWithPlaceholders";

export const MultipleQuestionsSingleChoiceForm: FC<
  MultipleQuestionFormProps
> = ({ questionText, questionAnswers, selectedOption, setSelectedOption }) => {
  const handleOptionChange: ChangeEventHandler<HTMLSelectElement> = (e) => {
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
      selectedOption={selectedOption}
      handleOptionChange={handleOptionChange}
    />
  );
};

export default MultipleQuestionsSingleChoiceForm;
