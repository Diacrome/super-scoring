import React, { ChangeEventHandler, FC, Fragment } from "react";
import {
  ANSWER_PLH,
  MultipleQuestionAnswers,
  SelectedOption,
} from "../types/questions";
import QuestionPlaceholder from "./QuestionPlaceholder";

interface QuestionTextWithPlaceholdersProps {
  text: string;
  selectedOption: SelectedOption;
  questionAnswers: MultipleQuestionAnswers;
  handleOptionChange: ChangeEventHandler<HTMLSelectElement>;
  getAnswerNumber: () => number;
}

const QuestionTextWithPlaceholders: FC<QuestionTextWithPlaceholdersProps> = ({
  text,
  selectedOption,
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
              selectedOption={selectedOption}
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
