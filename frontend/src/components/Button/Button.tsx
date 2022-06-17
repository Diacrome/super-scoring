import React, { ComponentPropsWithoutRef, FC } from "react";

const Button: FC<ComponentPropsWithoutRef<"button">> = (props) => {
  return <button className="btn" {...props} />;
};

export default Button;
