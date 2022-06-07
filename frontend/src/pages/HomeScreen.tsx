import React, { FC } from "react";
import { fetchStatus } from "../store/action-creators/status";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button/Button";
import { useAppDispatch } from "../hooks/useAppDispatch";

const HomeScreen: FC = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const logout = () => {
    localStorage.removeItem("Authorization");
    dispatch(fetchStatus());
  };

  const goToTest = () => {
    navigate("/1");
  };

  return (
    <>
      <div className="navbar">
        <div className="navbar-logout">
          <Button onClick={logout}>Выйти</Button>
        </div>
      </div>
      <div className="test-catalog">
        <div className="test-card">
          <Button onClick={goToTest}>Тест 1</Button>
        </div>
      </div>
    </>
  );
};

export default HomeScreen;
