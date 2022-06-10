import React, { FC } from "react";
import { fetchStatus } from "../store/action-creators/status";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button/Button";
import { useAppDispatch } from "../hooks/useAppDispatch";
import { setDefaultToken } from "../functions/setDefaultToken";

const HomeScreen: FC = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const tests = [1, 2, 3];

  const logout = () => {
    localStorage.removeItem("Authorization");
    setDefaultToken();
    dispatch(fetchStatus());
  };

  return (
    <>
      <div className="navbar">
        <div className="navbar-logout">
          <Button onClick={logout}>Выйти</Button>
        </div>
      </div>
      <div className="test-catalog">
        {tests.map((testId) => (
          <div className="test-card" key={testId}>
            <Button key={testId} onClick={() => navigate("/" + testId)}>
              Тест {testId}
            </Button>
          </div>
        ))}
      </div>
    </>
  );
};

export default HomeScreen;
