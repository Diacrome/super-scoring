import React, { FC } from "react";
import { useNavigate } from "react-router-dom";

interface TestCardComponentProps {
  id: number;
  name: string;
}

const TestCardComponent: FC<TestCardComponentProps> = ({ id, name }) => {
  const navigate = useNavigate();

  return (
    <button className="test-card" onClick={() => navigate(`/${id}`)}>
      {name}
    </button>
  );
};

export default TestCardComponent;
