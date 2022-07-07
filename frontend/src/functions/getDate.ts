export const getDate = (rawDate: string | number) => {
  let date: Date;
  switch (typeof rawDate) {
    case "string":
      date = new Date(`${rawDate}Z`);
      break;
    case "number":
      date = new Date(rawDate * 1000);
      break;
  }
  return `${date.toLocaleTimeString().slice(0, -3)}
    ${date.toLocaleDateString()}`;
};
