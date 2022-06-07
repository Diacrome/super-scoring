import React, { FC, FormEventHandler, useState } from "react";
import Button from "../components/Button/Button";
import TextInput from "../components/Input/TextInput";
import { LoginParams } from "../types/login";
import { useFetchToken } from "../hooks/useFetchToken";
import { userRegister } from "../functions/userRegister";

const LoginScreen: FC = () => {
  const fetchToken = useFetchToken();

  const [loginParams, setLoginParams] = useState<LoginParams>({
    login: "",
    password: "",
  });

  const handleSubmit: FormEventHandler = (e) => {
    e.preventDefault();
    fetchToken(loginParams);
  };

  const handleRegister = () => {
    userRegister(loginParams);
  };

  return (
    <form className="auth-form" onSubmit={handleSubmit} method="post">
      <div className="form-block">
        <label className="input-label" htmlFor="login">
          Логин
        </label>
        <TextInput
          name="login"
          id="login"
          value={loginParams.login}
          onChange={(event) =>
            setLoginParams({ ...loginParams, login: event.target.value })
          }
        />
      </div>
      <div className="form-block">
        <label className="input-label" htmlFor="password">
          Пароль
        </label>
        <TextInput
          type="password"
          name="password"
          id="password"
          value={loginParams.password}
          onChange={(event) =>
            setLoginParams({ ...loginParams, password: event.target.value })
          }
        />
      </div>
      <div className="form-block">
        <Button>Войти</Button>
      </div>
      <div className="form-block">
        <Button type="button" onClick={handleRegister}>
          Зарегистрироваться
        </Button>
      </div>
    </form>
  );
};

export default LoginScreen;
