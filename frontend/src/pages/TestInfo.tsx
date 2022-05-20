import React from "react";
import { useParams } from "react-router-dom";
import { useAppDispatch } from "../hooks/useAppDispatch";
import { fetchStatus, statusPass } from "../store/action-creators/status";
import Button from "../components/Button/Button";

const TestInfo = () => {
  const { testId } = useParams();
  const dispatch = useAppDispatch();

  const startTest = () => {
    localStorage.setItem("currentPass", JSON.stringify(statusPass));
    dispatch(fetchStatus());
  };

  return testId === "1" || testId === "2" ? (
    <div className="test-info">
      <div className="test-info__text">Какая-то информация о тесте</div>
      <div className="btn-block">
        <Button onClick={startTest}>Начать тест</Button>
      </div>
    </div>
  ) : (
    <div>Тест не существует</div>
  );
};

export default TestInfo;
