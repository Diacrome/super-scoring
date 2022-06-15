import React, { ChangeEventHandler, FC } from "react";
import { SingleQuestionFormProps } from "../types/questions";
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

  const handleOptionChange: ChangeEventHandler<
    HTMLInputElement | HTMLSelectElement
  > = (e) => {
    setSelectedOption([+e.target.value]);
  };

  return (
    <>
      {isSelectInputType ? (
        <QuestionFormWithPlaceholders
          questionText={questionText}
          questionAnswers={[questionAnswers]}
          selectedOption={selectedOption}
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
