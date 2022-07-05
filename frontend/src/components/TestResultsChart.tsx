import React, { FC, useEffect, useRef } from "react";
import { drawPieSlice } from "../functions/drawPieSlice";

interface TestResultsChartProps {
  ratio: number;
}

const TestResultsChart: FC<TestResultsChartProps> = ({ ratio }) => {
  const canvasRef = useRef<HTMLCanvasElement>(null);

  useEffect(() => {
    const canvas = canvasRef.current;
    if (canvas) {
      canvas.className = "results__chart";
      const ctx = canvas.getContext("2d") as CanvasRenderingContext2D;
      canvas.height = canvas.width;
      const radius = canvas.width / 2;
      const centerX = radius;
      const centerY = radius;
      const fullAngle = 2 * Math.PI;
      const startAngle = fullAngle * 0.75;

      drawPieSlice(ctx, centerX, centerY, radius, 0, fullAngle, "lightgray");
      drawPieSlice(
        ctx,
        centerX,
        centerY,
        radius,
        startAngle,
        startAngle + fullAngle * ratio,
        "#d91124ff"
      );
      drawPieSlice(ctx, centerX, centerY, radius * 0.5, 0, fullAngle, "white");
    }
  }, []);

  return (
    <div className="results__chart-wrapper">
      <canvas ref={canvasRef} />
      <div className="results__chart-text">{`${(ratio * 100).toFixed(
        0
      )}%`}</div>
    </div>
  );
};

export default TestResultsChart;
