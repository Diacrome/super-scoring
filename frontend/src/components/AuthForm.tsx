import React, { FC, FormEventHandler, useState } from "react";
import TextInput from "./Input/TextInput";
import Button from "./Button/Button";
import { FormProps, AuthParams } from "../types/login";
import { useFetchToken } from "../hooks/useFetchToken";

const AuthForm: FC<FormProps> = ({ handleMode }) => {
  const fetchToken = useFetchToken();
  const [authParams, setAuthParams] = useState<AuthParams>({
    login: "",
    password: "",
  });

  const handleSubmit: FormEventHandler = (e) => {
    e.preventDefault();
    fetchToken(authParams);
  };

  return (
    <form className="login-form" onSubmit={handleSubmit} method="post">
      <div className="form-block">
        <label className="input-label" htmlFor="login">
          Логин
        </label>
        <TextInput
          name="login"
          id="login"
          value={authParams.login}
          onChange={(event) =>
            setAuthParams({ ...authParams, login: event.target.value })
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
          value={authParams.password}
          onChange={(event) =>
            setAuthParams({ ...authParams, password: event.target.value })
          }
        />
      </div>
      <div className="form-block">
        <Button>Войти</Button>
      </div>
      <div className="form-block">
        <Button type="button" onClick={handleMode}>
          Зарегистрироваться
        </Button>
      </div>
    </form>
  );
};

export default AuthForm;
