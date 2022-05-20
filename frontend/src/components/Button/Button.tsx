import React, { FC, HTMLAttributes } from "react";

const Button: FC<HTMLAttributes<HTMLButtonElement>> = (props) => {
  return <button className="btn" {...props} />;
};

export default Button;
