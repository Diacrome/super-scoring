export type TestResults = TestResultsPass | TestResultsPassed;

export interface TestResultsPass {
  testPassId: number;
  status: TestStatus.Pass;
  finalScore: null;
  maxPossible: null;
  qualificationName: null;
  endDateTime: null;
  startDateTime: string;
  testId: number;
  testName: string;
  name: string;
  surname: string | null;
  patronymic: string | null;
}

export interface TestResultsPassed {
  testPassId: number;
  status: TestStatus.Passed;
  finalScore: number;
  maxPossible: number;
  qualificationName: string;
  endDateTime: string;
  startDateTime: string;
  testId: number;
  testName: string;
  name: string;
  surname: string | null;
  patronymic: string | null;
}

export enum TestStatus {
  Pass = "PASS",
  Passed = "PASSED",
}
