import React, { FC } from "react";

interface QuestionTextProps {
  questionText: string[];
}

const QuestionText: FC<QuestionTextProps> = ({ questionText }) => {
  return (
    <div className="test__question-text">
      {questionText.map((paragraphText) => (
        <p className="test__question-paragraph" key={paragraphText}>
          {paragraphText}
        </p>
      ))}
    </div>
  );
};

export default QuestionText;
