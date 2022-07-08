import React, { FC } from "react";
import { ContentType, QuestionContentProps } from "../types/questions";
import { contentLocation } from "../types/locations";
import ReactAudioPlayer from "react-audio-player";

const QuestionContent: FC<QuestionContentProps> = ({ content }) => {
  const contentUrl = `${contentLocation}/${content[0].url}`;
  console.log(contentUrl);

  switch (content[0].type) {
    case ContentType.Image:
      return (
        <img
          className="test__content"
          src={contentUrl}
          alt="Картинка к вопросу"
        />
      );
    case ContentType.Video:
      return <video className="test__content" src={contentUrl} controls />;
    case ContentType.Audio:
      return (
        <ReactAudioPlayer className="test__content" src={contentUrl} controls />
      );
  }
  return <></>;
};

export default QuestionContent;
