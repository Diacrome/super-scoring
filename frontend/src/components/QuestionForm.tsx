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
import { getSelectedOptionInitialState } from "../functions/getSelectedOptionInitialState";
import Button from "./Button/Button";
import { useCancelPass } from "../hooks/useCancelPass";

const QuestionForm: FC<QuestionFormProps> = ({
  questionText,
  questionAnswers,
  answerType,
  questionOrder,
  questionCount,
}) => {
  const [selectedOption, setSelectedOption] = useState<SelectedOption>(
    getSelectedOptionInitialState(answerType, questionAnswers)
  );
  const cancelPass = useCancelPass();

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
      <div className="test__btns">
        <TestAnswerButton
          questionOrder={questionOrder}
          questionsCount={questionCount}
          selectedOption={selectedOption}
          answerType={answerType}
        />
        <div className="test__btn-cancel">
          <Button onClick={() => cancelPass()}>Отменить прохождение</Button>
        </div>
      </div>
    </>
  );
};

export default QuestionForm;
