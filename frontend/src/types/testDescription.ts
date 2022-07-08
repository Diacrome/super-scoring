export interface TestDescription {
  id: number;
  name: string;
  description: string;
  timeLimit: number;
}

export type TestId = string | undefined;
