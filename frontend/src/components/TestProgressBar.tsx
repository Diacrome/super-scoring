import React, { FC } from "react";

interface TestProgressBarProps {
  progressPercent: number;
}

const TestProgressBar: FC<TestProgressBarProps> = ({ progressPercent }) => {
  return (
    <div className="test__progress">
      <div className="test__progress-bar">
        <div className="test__progress-full-bar" />
        <div
          className="test__progress-pass-bar"
          style={{ width: `${progressPercent}%` }}
        />
      </div>
      <div className="test__progress-text">{`${progressPercent.toFixed(
        0
      )}%`}</div>
    </div>
  );
};

export default TestProgressBar;
