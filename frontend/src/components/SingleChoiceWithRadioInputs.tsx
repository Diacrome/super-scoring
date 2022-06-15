import React, { FC } from "react";
import { QuestionFormWithInputsProps } from "../types/questions";
import QuestionText from "./QuestionText";

const SingleChoiceWithRadioInputs: FC<QuestionFormWithInputsProps> = ({
  questionText,
  questionAnswers,
  selectedOption,
  handleOptionChange,
}) => {
  return (
    <>
      <QuestionText questionText={questionText} />
      {questionAnswers.map((answer, number) => (
        <div className="option" key={number + 1}>
          <input
            id={"option-" + number + 1}
            type="radio"
            name="option"
            value={number + 1}
            checked={selectedOption[0] === number + 1}
            onChange={handleOptionChange}
          />
          <label className="label-option" htmlFor={"option-" + (number + 1)}>
            {answer}
          </label>
        </div>
      ))}
    </>
  );
};

export default SingleChoiceWithRadioInputs;
