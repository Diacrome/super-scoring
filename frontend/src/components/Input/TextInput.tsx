import React, { ComponentPropsWithoutRef, FC } from "react";

const TextInput: FC<ComponentPropsWithoutRef<"input">> = (props) => {
  return <input className="text-input" {...props} />;
};

export default TextInput;
