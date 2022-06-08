import React, { FC, useEffect, useState } from "react";
import Button from "../components/Button/Button";
import { getTestDecription } from "../functions/getTestDecription";
import { TestDescription } from "../types/testDescription";
import Loader from "../components/Loader/Loader";
import { useParams } from "react-router-dom";
import { useTestStart } from "../hooks/useTestStart";

const TestInfo: FC = () => {
  const { testId } = useParams();
  const [testDescription, setTestDescription] =
    useState<TestDescription | null>(null);
  const testStart = useTestStart();

  useEffect(() => {
    getTestDecription(testId).then((data) => setTestDescription(data));
  }, []);

  const handleTestStart = () => testStart(testId);

  if (testDescription === null) {
    return <Loader />;
  } else {
    return testId === "1" || testId === "2" ? (
      <div className="test-info">
        <div className="test-info__text">{testDescription.description}</div>
        <div className="btn-block">
          <Button onClick={handleTestStart}>Начать тест</Button>
        </div>
      </div>
    ) : (
      <div>Тест не существует</div>
    );
  }
};

export default TestInfo;
