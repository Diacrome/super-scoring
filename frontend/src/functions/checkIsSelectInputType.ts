import { ANSWER_PLH } from "../types/questions";

export const checkIsSelectInputType = (text: string[]) => {
  for (const part of text) {
    if (part.match(ANSWER_PLH)) return true;
  }
  return false;
};
