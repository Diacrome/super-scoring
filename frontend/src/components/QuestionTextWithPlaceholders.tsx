import React, { FC, Fragment } from "react";
import {
  ANSWER_PLH,
  QuestionTextWithPlaceholdersProps,
} from "../types/questions";
import QuestionPlaceholder from "./QuestionPlaceholder";

const QuestionTextWithPlaceholders: FC<QuestionTextWithPlaceholdersProps> = ({
  text,
  questionAnswers,
  handleOptionChange,
  getAnswerNumber,
}) => {
  const textParts = text.split(ANSWER_PLH);

  // Вставляем select перед каждой частью текста, кроме первой
  return (
    <>
      {textParts.map((part, number) => (
        <Fragment key={number}>
          {!!number && (
            <QuestionPlaceholder
              handleOptionChange={handleOptionChange}
              questionAnswers={questionAnswers}
              questionNumber={getAnswerNumber()}
            />
          )}
          {part}
        </Fragment>
      ))}
    </>
  );
};

export default QuestionTextWithPlaceholders;
