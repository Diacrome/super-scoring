import React, { ChangeEventHandler, FC } from "react";
import { MultipleQuestionAnswers, SelectedOption } from "../types/questions";

export interface QuestionPlaceholderProps {
  selectedOption: SelectedOption;
  handleOptionChange: ChangeEventHandler<HTMLSelectElement>;
  questionAnswers: MultipleQuestionAnswers;
  questionNumber: number;
}

const QuestionPlaceholder: FC<QuestionPlaceholderProps> = ({
  selectedOption,
  handleOptionChange,
  questionAnswers,
  questionNumber,
}) => {
  return (
    <select
      name={`${questionNumber}`}
      value={selectedOption[questionNumber]}
      onChange={handleOptionChange}
    >
      <option value={0} disabled></option>
      {questionAnswers[questionNumber].map((answer, number) => (
        <option key={number} value={number + 1}>
          {answer}
        </option>
      ))}
    </select>
  );
};

export default QuestionPlaceholder;
