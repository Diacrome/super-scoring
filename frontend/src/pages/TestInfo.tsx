import React, { FC, useEffect, useState } from "react";
import Button from "../components/Button/Button";
import { TestDescription } from "../types/testDescription";
import Loader from "../components/Loader/Loader";
import { useParams } from "react-router-dom";
import { useTestStart } from "../hooks/useTestStart";
import { fetchData } from "../functions/fetchData";

const TestInfo: FC = () => {
  const { testId } = useParams();
  const [testDescription, setTestDescription] =
    useState<TestDescription | null>(null);
  const testStart = useTestStart();

  useEffect(() => {
    fetchData(`test/info/${testId}`).then((data) => setTestDescription(data));
  }, []);

  const handleTestStart = () => testStart(testId);

  if (testDescription === null) return <Loader />;

  return testId === "1" || testId === "2" || testId === "3" ? (
    <div className="test">
      <div className="test-info-text">{testDescription.description}</div>
      <div className="btn-start">
        <Button onClick={handleTestStart}>Начать тест</Button>
      </div>
    </div>
  ) : (
    <div>Тест не существует</div>
  );
};

export default TestInfo;
