export const getDate = (stringDate: string) => {
  const date = new Date(`${stringDate}Z`);
  return `${date.toLocaleTimeString().slice(0, -3)}
    ${date.toLocaleDateString()}`;
};
