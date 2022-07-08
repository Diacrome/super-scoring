import React, { FC, useEffect, useState } from "react";
import { fetchData } from "../functions/fetchData";
import { TestDescription } from "../types/testDescription";
import Timer from "./Timer";

interface TestTimerProps {
  testId: number;
  startTime: string;
}

const TestTimer: FC<TestTimerProps> = ({ testId, startTime }) => {
  const [testLimit, setTestLimit] = useState<number | null>(null);

  useEffect(() => {
    fetchData(`test/info/${testId}`).then((testDescription: TestDescription) =>
      setTestLimit(testDescription.timeLimit)
    );
  }, []);

  if (testLimit) {
    return <Timer testLimit={testLimit} startTime={startTime} />;
  }
  return <></>;
};

export default TestTimer;
