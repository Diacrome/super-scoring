import { StylesConfig } from "react-select";
import { ReactSelectedOption } from "../types/questions";

const HEIGHT = "20px";

export const QuestionSelectStyle: StylesConfig<ReactSelectedOption, false> = {
  container: (provided) => ({
    ...provided,
    display: "inline-block",
    minWidth: "150px",
  }),

  control: (provided) => ({
    ...provided,
    background: "#fff",
    borderColor: "#9e9e9e",
    minHeight: HEIGHT,
    height: HEIGHT,
  }),

  valueContainer: (provided) => ({
    ...provided,
    height: HEIGHT,
    minHeight: HEIGHT,
    padding: "0 6px",
  }),

  input: (provided) => ({
    ...provided,
    margin: "0px",
    padding: 0,
    height: HEIGHT,
    minHeight: HEIGHT,
  }),
  indicatorSeparator: () => ({
    display: "none",
  }),
  indicatorsContainer: (provided) => ({
    ...provided,
    height: HEIGHT,
    minHeight: HEIGHT,
  }),

  singleValue: (provided) => ({
    ...provided,
    height: HEIGHT,
    minHeight: HEIGHT,
  }),
};
