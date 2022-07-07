import React, { FC, useEffect, useState } from "react";
import Button from "../components/Button/Button";
import { TestDescription } from "../types/testDescription";
import Loader from "../components/Loader/Loader";
import { useParams } from "react-router-dom";
import { useTestStart } from "../hooks/useTestStart";
import { fetchData } from "../functions/fetchData";
import { AttemptsInfo, StartResult } from "../types/attempts";
import { getDate } from "../functions/getDate";

const TestInfo: FC = () => {
  const { testId } = useParams();
  const [testDescription, setTestDescription] =
    useState<TestDescription | null>(null);
  const [attemptsInfo, setAttemptsInfo] = useState<AttemptsInfo | null>(null);
  const testStart = useTestStart();

  useEffect(() => {
    fetchData(`test/info/${testId}`).then((data) => setTestDescription(data));
  }, []);

  const handleTestStart = () =>
    testStart(testId).then((data) => {
      setAttemptsInfo(data);
    });

  if (testDescription === null) return <Loader />;

  if (testDescription === undefined)
    return (
      <div className="test">
        <h1 className="not-exist-text">Тест не существует</h1>
      </div>
    );

  if (attemptsInfo) {
    return (
      <div className="test attempts-info">
        <div>
          {attemptsInfo.startResult === StartResult.Spent &&
            "Истрачены три разрешнные попытки."}
          {attemptsInfo.startResult === StartResult.Passed &&
            "Тест успешно пройден."}
        </div>
        <div>{`Следующая попытка: ${getDate(attemptsInfo.nextAttempt)}`}</div>
      </div>
    );
  }

  return (
    <div className="test">
      <div className="test-info-text">{testDescription.description}</div>
      <div className="btn-start">
        <Button onClick={handleTestStart}>Начать тест</Button>
      </div>
    </div>
  );
};

export default TestInfo;
