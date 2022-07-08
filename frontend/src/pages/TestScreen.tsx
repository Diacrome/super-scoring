import React, { FC, useEffect, useState } from "react";
import { Questions } from "../types/questions";
import Loader from "../components/Loader/Loader";
import QuestionForm from "../components/QuestionForm";
import { useAppSelector } from "../hooks/useAppSelector";
import { getQuestionAnswers } from "../functions/getQuestionAnswers";
import QuestionContent from "../components/QuestionContent";
import { fetchData } from "../functions/fetchData";
import TestProgressBar from "../components/TestProgressBar";
import TestTimer from "../components/TestTimer";
const TestScreen: FC = () => {
  const { currentPass } = useAppSelector((state) => state.status);
  const [questions, setQuestions] = useState<Questions | null>(null);

  if (!currentPass) {
    throw "Ошибка прохождения теста";
  }

  const questionOrder =
    Object.values(currentPass.answeredQuestions).indexOf(false) + 1;

  useEffect(() => {
    fetchData("questions").then((response) => setQuestions(response.question));
  }, []);

  if (questions === null) return <Loader />;

  const question = questions[questionOrder];
  const answerType = question.answerType;
  const questionContent = question.content;
  const questionText = question.question.split("\\n");
  const questionAnswers = getQuestionAnswers(answerType, question);
  const questionsCount = Object.keys(questions).length;

  return (
    <div className="test">
      <TestProgressBar
        progressPercent={((questionOrder - 1) / questionsCount) * 100}
      />
      <div className="test__title">
        <div className="test__title-text">Question {questionOrder}:</div>
        <TestTimer
          testId={currentPass.testId}
          startTime={currentPass.startTime}
        />
      </div>
      {questionContent && <QuestionContent content={questionContent} />}
      <QuestionForm
        questionText={questionText}
        questionAnswers={questionAnswers}
        answerType={answerType}
        questionOrder={questionOrder}
        questionCount={questionsCount}
      />
    </div>
  );
};

export default TestScreen;
