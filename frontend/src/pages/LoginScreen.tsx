import React, { FC } from "react";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { signIn } from "../store/action-creators/status";
import Button from "../components/Button/Button";

const LoginScreen: FC = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const auth = () => {
    localStorage.setItem("authorized", "true");
    dispatch(signIn());
    navigate("/");
  };

  return (
    <div className="login-block">
      <Button onClick={auth}>Войти</Button>
    </div>
  );
};

export default LoginScreen;
