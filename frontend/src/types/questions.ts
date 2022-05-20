export type Questions = Record<
  string,
  {
    question: string;
    payload: Record<string, number>;
  }
>;
