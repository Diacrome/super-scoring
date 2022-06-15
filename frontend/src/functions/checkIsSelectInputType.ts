import { ANSWER_PLH } from "../types/questions";

export const checkIsSelectInputType = (text: string[]) => {
  text.forEach((part) => {
    if (part.match(ANSWER_PLH)) return true;
  });
  return false;
};
