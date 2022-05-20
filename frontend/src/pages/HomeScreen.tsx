import React, { FC } from "react";
import { logOut } from "../store/action-creators/status";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button/Button";

const HomeScreen: FC = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const auth = () => {
    localStorage.setItem("authorized", "false");
    dispatch(logOut());
  };

  const goToTest = () => {
    navigate("/1");
  };

  return (
    <div className="navbar">
      <Button onClick={goToTest}>Тест 1</Button>
      <div className="navbar-logout">
        <Button onClick={auth}>Выйти</Button>
      </div>
    </div>
  );
};

export default HomeScreen;
