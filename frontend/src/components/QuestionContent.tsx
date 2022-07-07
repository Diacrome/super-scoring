import React, { FC } from "react";
import { ContentType, QuestionContentProps } from "../types/questions";
import { contentLocation } from "../types/locations";

const QuestionContent: FC<QuestionContentProps> = ({ content }) => {
  const contentUrl = `${contentLocation}/${content[0].url}`;

  switch (content[0].type) {
    case ContentType.Image:
      return (
        <img
          className="test_content"
          src={contentUrl}
          alt="Картинка к вопросу"
        />
      );
    case ContentType.Video:
      return (
        <video
          className="test_content"
          src={contentUrl}
          controls={true}
        ></video>
      );
  }
  return <></>;
};

export default QuestionContent;
