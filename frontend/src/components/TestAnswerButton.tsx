import React, { FC } from "react";
import Button from "./Button/Button";
import { useNavigate } from "react-router-dom";
import { TestAnswerButtonProps } from "../types/questions";
import { useSendAnswer } from "../hooks/useSendAnswer";
import { IsSelectedOption } from "../functions/isSelectedOption";

const TestAnswerButton: FC<TestAnswerButtonProps> = ({
  questionOrder,
  questionsCount,
  selectedOption,
  answerType,
}) => {
  const sendAnswer = useSendAnswer();
  const navigate = useNavigate();

  const nextQuestion = () => {
    if (IsSelectedOption(selectedOption, answerType)) {
      sendAnswer(questionOrder, selectedOption, answerType);
    }
  };

  const endTest = () => {
    if (IsSelectedOption(selectedOption, answerType)) {
      sendAnswer(questionOrder, selectedOption, answerType);
      navigate(`/3/1`); // Тут надо сделать по id теста
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
