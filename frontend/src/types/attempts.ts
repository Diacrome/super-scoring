export interface AttemptsInfo {
  startResult: StartResult;
  attempts: number | null;
  nextAttempt: number;
}

export enum StartResult {
  Spent = "SPENT",
  Passed = "PASSED",
}
