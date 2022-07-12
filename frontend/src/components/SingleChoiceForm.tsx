import React, { FC } from "react";
import {
  HandleOptionChange,
  SingleQuestionFormProps,
} from "../types/questions";
import SingleChoiceWithRadioInputs from "./SingleChoiceWithRadioInputs";
import QuestionFormWithPlaceholders from "./QuestionFormWithPlaceholders";
import { checkIsSelectInputType } from "../functions/checkIsSelectInputType";

const SingleChoiceForm: FC<SingleQuestionFormProps> = ({
  questionText,
  questionAnswers,
  selectedOption,
  setSelectedOption,
}) => {
  const isSelectInputType = checkIsSelectInputType(questionText);

  const handleOptionChange: HandleOptionChange = (e) => {
    setSelectedOption([+e.target.value]);
  };

  return (
    <>
      {isSelectInputType ? (
        <QuestionFormWithPlaceholders
          questionText={questionText}
          questionAnswers={[questionAnswers]}
          handleOptionChange={handleOptionChange}
        />
      ) : (
        <SingleChoiceWithRadioInputs
          questionText={questionText}
          questionAnswers={questionAnswers}
          selectedOption={selectedOption}
          handleOptionChange={handleOptionChange}
        />
      )}
    </>
  );
};

export default SingleChoiceForm;
