import React, { FC, useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { fetchData } from "../functions/fetchData";
import Loader from "../components/Loader/Loader";
import { TestResults, TestStatus } from "../types/results";
import { getDate } from "../functions/getDate";
import TestResultsChart from "../components/TestResultsChart";

const Results: FC = () => {
  const { testPassId } = useParams();

  const [testResults, setTestResults] = useState<TestResults | null>(null);

  useEffect(() => {
    fetchData(`test/results/${testPassId}`).then((data) =>
      setTestResults(data)
    );
  }, []);

  if (testResults === null) return <Loader />;

  if (testResults === undefined) {
    return (
      <div className="results__title">
        <h1 className="results__title-text">Страница не существует</h1>
      </div>
    );
  }

  switch (testResults.status) {
    case TestStatus.Pass:
      return (
        <div className="results__title">
          <h1 className="results__title-text">Тест не завершён</h1>
        </div>
      );
    case TestStatus.Passed:
      return (
        <div className="results">
          <div className="results__title">
            <h1>Поздравляем с завершением теста!</h1>
          </div>
          <div>
            <div className="results__test-name">
              <h2>{testResults.testName}</h2>
            </div>
            <div className="results__content">
              <div className="results__info">
                <h1 className="results__user">
                  {testResults.surname}
                  {testResults.name}
                  {testResults.patronymic}
                </h1>
                <h2>{`Результат: ${testResults.finalScore}/${testResults.maxPossible}`}</h2>
                <p>{`Уровень: ${testResults.qualificationName}`}</p>
                <div className="results__time">
                  <p>{`Время начала теста: ${getDate(
                    testResults.startDateTime
                  )}`}</p>
                  <p>{`Время окончание теста: ${getDate(
                    testResults.endDateTime
                  )}`}</p>
                </div>
              </div>
              <TestResultsChart
                ratio={testResults.finalScore / testResults.maxPossible}
              />
            </div>
          </div>
        </div>
      );
  }
};

export default Results;
