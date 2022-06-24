import React, { FC } from "react";
import {
  ANSWER_PLH,
  QuestionFormWithPlaceholdersProps,
} from "../types/questions";
import QuestionTextWithPlaceholders from "./QuestionTextWithPlaceholders";

const QuestionFormWithPlaceholders: FC<QuestionFormWithPlaceholdersProps> = ({
  questionText,
  questionAnswers,
  selectedOption,
  handleOptionChange,
}) => {
  return (
    <div className="test__question-text">
      {questionText.map((paragraphText) => (
        <p className="test__question-paragraph" key={paragraphText}>
          {paragraphText.match(ANSWER_PLH) ? (
            <QuestionTextWithPlaceholders
              text={paragraphText}
              selectedOption={selectedOption}
              questionAnswers={questionAnswers}
              handleOptionChange={handleOptionChange}
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
