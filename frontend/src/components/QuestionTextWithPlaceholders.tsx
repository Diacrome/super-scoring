import React, { ChangeEventHandler, FC, Fragment } from "react";
import { ANSWER_PLH, SelectedOption } from "../types/questions";

interface QuestionTextWithPlaceholdersProps {
  text: string;
  selectedOption: SelectedOption;
  questionAnswers: string[][];
  handleOptionChange: ChangeEventHandler<HTMLSelectElement>;
}

const QuestionTextWithPlaceholders: FC<QuestionTextWithPlaceholdersProps> = ({
  text,
  selectedOption,
  questionAnswers,
  handleOptionChange,
}) => {
  const textParts = text.split(ANSWER_PLH);

  // Вставляем select перед каждой частью текста, кроме первой
  return (
    <>
      {textParts.map((part, number) => (
        <Fragment key={number}>
          number &&
          <select
            name={`${number - 1}`}
            value={selectedOption[number - 1]}
            onChange={handleOptionChange}
          >
            <option value={0} disabled></option>
            {questionAnswers[number - 1].map((answer, number) => (
              <option key={number} value={number + 1}>
                {answer}
              </option>
            ))}
          </select>
          {part}
        </Fragment>
      ))}
    </>
  );
};

export default QuestionTextWithPlaceholders;
