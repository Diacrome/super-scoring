import React, { FC } from "react";
import Button from "../components/Button/Button";
import { fetchStatus } from "../store/action-creators/status";
import { useNavigate } from "react-router-dom";
import { Questions } from "../types/questions";

const TestScreen: FC = () => {
  const currentQuestion = localStorage.getItem("currentQuestion") || "1";
  const questionInfo = questions[currentQuestion];
  const navigate = useNavigate();

  const nextQuestion = () => {
    localStorage.setItem("currentQuestion", +currentQuestion + 1 + "");
    fetchStatus();
  };

  const endTest = () => {
    localStorage.setItem("currentPass", "null");
    localStorage.setItem("currentQuestion", "1");
    fetchStatus();
    navigate("/1/1");
  };

  return (
    <div className="test">
      <div>Вопрос {currentQuestion}:</div>
      <div>{questionInfo.question}</div>
      <form>
        {[1, 2, 3, 4].map((elem) => (
          <div className="choice" key={elem}>
            <input id={elem + ""} type="radio" name="answer" value={elem} />
            <label className="label-choice" htmlFor={`choice${elem}`}>
              {questionInfo.payload[elem]}
            </label>
          </div>
        ))}
        <div className="btn-block">
          {currentQuestion != "3" ? (
            <Button onClick={nextQuestion}>Ответить</Button>
          ) : (
            <Button onClick={endTest}>Завершить тест</Button>
          )}
        </div>
      </form>
    </div>
  );
};

const questions: Questions = {
  "1": {
    question: "5*4 = ",
    payload: { "1": 1, "2": 3, "3": 4, "4": 20 },
  },
  "2": {
    question: "1*1 = ",
    payload: { "1": 1, "2": 3, "3": 4, "4": 1 },
  },
  "3": {
    question: "3*3 = ",
    payload: { "1": 1, "2": 3, "3": 9, "4": 1 },
  },
};

export default TestScreen;
