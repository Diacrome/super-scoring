import React, { FC } from "react";
import { ContentType, QuestionContentProps } from "../types/questions";

const QuestionContent: FC<QuestionContentProps> = ({ content }) => {
  switch (content[0].type) {
    case ContentType.Image:
      return (
        <img
          className="test_content"
          src={`http://localhost:9999/${content[0].url}`}
          alt="Картинка к вопросу"
        />
      );
    case ContentType.Video:
      return (
        <video
          className="test_content"
          src={`http://localhost:9999/${content[0].url}`}
          controls={true}
        ></video>
      );
  }
  return <></>;
};

export default QuestionContent;
