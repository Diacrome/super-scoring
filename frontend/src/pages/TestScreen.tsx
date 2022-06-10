import React, { ChangeEventHandler, FC, useEffect, useState } from "react";
import Button from "../components/Button/Button";
import { useNavigate } from "react-router-dom";
import { Questions } from "../types/questions";
import { fetchQuestions } from "../functions/fetchQuestions";
import Loader from "../components/Loader/Loader";
import { useAppDispatch } from "../hooks/useAppDispatch";
import { fetchStatus } from "../store/action-creators/status";

const TestScreen: FC = () => {
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const [questions, setQuestions] = useState<Questions | null>(null);
  const [selectedOption, setSelectedOption] = useState<string>("");

  const currentQuestion = localStorage.getItem("currentQuestion") || "1";

  useEffect(() => {
    fetchQuestions().then((response) => setQuestions(response.question));
  }, []);

  const handleOptionChange: ChangeEventHandler<HTMLInputElement> = (e) => {
    setSelectedOption(e.target.value);
  };

  const nextQuestion = () => {
    if (selectedOption) {
      localStorage.setItem("currentQuestion", +currentQuestion + 1 + "");
      dispatch(fetchStatus());
    }
  };

  const endTest = () => {
    if (selectedOption) {
      localStorage.removeItem("currentQuestion");
      dispatch(fetchStatus);
      navigate(`/3/1`);
    }
  };

  if (questions === null) {
    return <Loader />;
  } else {
    const questionText = questions[currentQuestion].question
      .replace("%answer", "______")
      .split("\\n");
    const questionAnswers = Object.entries(questions[currentQuestion].payload);
    const questionsCount = Object.keys(questions).length;
    return (
      <div className="test">
        <div>Вопрос {currentQuestion}:</div>
        <div className="test__question-text">
          {questionText.map((paragraphText) => (
            <p className="test__question-paragraph" key={paragraphText}>
              {paragraphText}
            </p>
          ))}
        </div>
        <form>
          {questionAnswers.map(([number, answer]) => (
            <div className="option" key={number}>
              <input
                id={"option-" + number}
                type="radio"
                name="option"
                value={number}
                checked={selectedOption === number}
                onChange={handleOptionChange}
              />
              <label className="label-option" htmlFor={"option-" + number}>
                {answer}
              </label>
            </div>
          ))}
          <div className="test__btn-answer">
            {currentQuestion != questionsCount + "" ? (
              <Button type="button" onClick={nextQuestion}>
                Ответить
              </Button>
            ) : (
              <Button type="button" onClick={endTest}>
                Завершить тест
              </Button>
            )}
          </div>
        </form>
      </div>
    );
  }
};

export default TestScreen;
