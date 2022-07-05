import React, { FC } from "react";
import Button from "./Button/Button";
import { useNavigate } from "react-router-dom";
import { TestAnswerButtonProps } from "../types/questions";
import { useSendAnswer } from "../hooks/useSendAnswer";
import { isSelectedOption } from "../functions/isSelectedOption";
import { useAppSelector } from "../hooks/useAppSelector";
import { CurrentPass } from "../types/status";

const TestAnswerButton: FC<TestAnswerButtonProps> = ({
  questionOrder,
  questionsCount,
  selectedOption,
  answerType,
}) => {
  const currentPass = useAppSelector(
    (state) => state.status.currentPass as CurrentPass
  );
  const sendAnswer = useSendAnswer();
  const navigate = useNavigate();

  const nextQuestion = () => {
    if (isSelectedOption(selectedOption, answerType)) {
      sendAnswer(questionOrder, selectedOption, answerType);
    }
  };

  const endTest = () => {
    if (isSelectedOption(selectedOption, answerType)) {
      sendAnswer(questionOrder, selectedOption, answerType).then(() =>
        navigate(`/${currentPass.testId}/${currentPass.testPassId}`)
      );
    }
  };

  return (
    <div className="test__btn-answer">
      {questionOrder != questionsCount ? (
        <Button type="button" onClick={nextQuestion}>
          Ответить
        </Button>
      ) : (
        <Button type="button" onClick={endTest}>
          Завершить тест
        </Button>
      )}
    </div>
  );
};

export default TestAnswerButton;
