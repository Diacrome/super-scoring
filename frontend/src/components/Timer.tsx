import React, { FC, useEffect, useState } from "react";
import { getTimer } from "../functions/getTimer";
import { TimerType } from "../types/testTimer";

export interface TimerProps {
  testLimit: number;
  startTime: string;
}

const Timer: FC<TimerProps> = ({ testLimit, startTime }) => {
  const [[hours, minutes, seconds], setTimer] = useState<TimerType>(
    getTimer(testLimit, startTime)
  );

  useEffect(() => {
    const timerID = setInterval(
      () => setTimer(getTimer(testLimit, startTime)),
      1000
    );
    return () => clearInterval(timerID);
  }, []);

  return (
    <div className="test__timer">{`${hours
      .toString()
      .padStart(2, "0")}:${minutes.toString().padStart(2, "0")}:${seconds
      .toString()
      .padStart(2, "0")}`}</div>
  );
};

export default Timer;
