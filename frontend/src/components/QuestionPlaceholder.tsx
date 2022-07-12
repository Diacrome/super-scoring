import React, { FC, useState } from "react";
import {
  HandleOptionChangeEvent,
  QuestionPlaceholderProps,
  ReactSelectedOption,
} from "../types/questions";
import Select, { SingleValue } from "react-select";
import { QuestionSelectStyle } from "../styles/QuestionSelectStyle";

const QuestionPlaceholder: FC<QuestionPlaceholderProps> = ({
  handleOptionChange,
  questionAnswers,
  questionNumber,
}) => {
  const options: ReactSelectedOption[] = [
    { value: 0, label: "Select...", disabled: true },
    ...questionAnswers[questionNumber].map((answer, number) => ({
      value: number + 1,
      label: answer,
    })),
  ];

  const [reactSelectedOption, setReactSelectedOption] =
    useState<ReactSelectedOption>(options[0]);

  const handleReactOptionChange = (
    option: SingleValue<ReactSelectedOption>
  ) => {
    if (option) {
      setReactSelectedOption(option);
      const e: HandleOptionChangeEvent = {
        target: {
          value: option.value,
          name: questionNumber,
        },
      };
      handleOptionChange(e);
    }
  };

  return (
    <Select
      styles={QuestionSelectStyle}
      value={reactSelectedOption}
      onChange={handleReactOptionChange}
      options={options}
      isOptionDisabled={(option) => Boolean(option.disabled)}
      isSearchable={false}
    />
  );
};

export default QuestionPlaceholder;
