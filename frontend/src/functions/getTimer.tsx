import { TimerType } from "../types/testTimer";

export const getTimer = (testLimit: number, startTime: string) => {
  const start = new Date(`${startTime}Z`).getTime() / 1000;
  const now = Math.floor(Date.now() / 1000);
  const timer = testLimit - (now - start);
  if (timer > 0) {
    return [
      Math.floor(timer / 3600),
      Math.floor(timer / 60) % 60,
      timer % 60,
    ] as TimerType;
  }
  return [0, 0, 0] as TimerType;
};
