import React, { FC } from "react";
import {
  ANSWER_PLH,
  QuestionFormWithPlaceholdersProps,
} from "../types/questions";
import QuestionTextWithPlaceholders from "./QuestionTextWithPlaceholders";

const QuestionFormWithPlaceholders: FC<QuestionFormWithPlaceholdersProps> = ({
  questionText,
  questionAnswers,
  handleOptionChange,
}) => {
  let answerNumber = 0;

  const getAnswerNumber = () => {
    return answerNumber++;
  };

  return (
    <div className="test__question-text">
      {questionText.map((paragraphText, number) => (
        <p className="test__question-paragraph" key={number}>
          {paragraphText.match(ANSWER_PLH) ? (
            <QuestionTextWithPlaceholders
              text={paragraphText}
              questionAnswers={questionAnswers}
              handleOptionChange={handleOptionChange}
              getAnswerNumber={getAnswerNumber}
            />
          ) : (
            paragraphText
          )}
        </p>
      ))}
    </div>
  );
};
export default QuestionFormWithPlaceholders;
