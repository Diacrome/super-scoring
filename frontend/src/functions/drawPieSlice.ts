export const drawPieSlice = (
  ctx: CanvasRenderingContext2D,
  centerX: number,
  centerY: number,
  radius: number,
  startAngle: number,
  endAngle: number,
  color: string
) => {
  ctx.beginPath();
  ctx.moveTo(centerX, centerY);
  ctx.arc(centerX, centerY, radius, startAngle, endAngle);
  ctx.fillStyle = color;
  ctx.fill();
  ctx.closePath();
};
