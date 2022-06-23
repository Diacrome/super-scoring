import React, { ChangeEventHandler, FC } from "react";
import { SingleQuestionFormProps } from "../types/questions";
import QuestionText from "./QuestionText";

const MultipleChoiceForm: FC<SingleQuestionFormProps> = ({
  questionText,
  questionAnswers,
  selectedOption,
  setSelectedOption,
}) => {
  const handleOptionChange: ChangeEventHandler<HTMLInputElement> = (e) => {
    setSelectedOption(
      selectedOption.map((checked, value) =>
        value == +e.target.value ? +e.target.checked : checked
      )
    );
  };
  return (
    <>
      <QuestionText questionText={questionText} />
      {questionAnswers.map((answer, number) => (
        <div className="option" key={number}>
          <input
            id={"option-" + number}
            type="checkbox"
            name="option"
            value={number}
            checked={!!selectedOption[+number]}
            onChange={handleOptionChange}
          />
          <label className="label-option" htmlFor={`option-${number}`}>
            {answer}
          </label>
        </div>
      ))}
    </>
  );
};

export default MultipleChoiceForm;
