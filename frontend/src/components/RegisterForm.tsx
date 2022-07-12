import React, { FC, FormEventHandler, useState } from "react";
import TextInput from "./Input/TextInput";
import Button from "./Button/Button";
import { FormProps, IsRegister, RegisterParams } from "../types/login";
import { userRegister } from "../functions/userRegister";

const RegisterForm: FC<FormProps> = ({ handleMode }) => {
  const [registerParams, setRegisterParams] = useState<RegisterParams>({
    name: "",
    login: "",
    password: "",
  });
  const [isRegistered, setIsRegistered] = useState<IsRegister>(IsRegister.No);

  const handleRegister: FormEventHandler = (e) => {
    e.preventDefault();
    userRegister(registerParams, setIsRegistered);
  };

  if (isRegistered === IsRegister.Yes) {
    return (
      <>
        <div className="register-text">Вы успешно зарегистрированы</div>
        <Button onClick={handleMode}>Войти</Button>
      </>
    );
  }

  return (
    <form className="login-form" onSubmit={handleRegister} method="post">
      {isRegistered === IsRegister.Bad && (
        <div className="form-block">
          Такой пользователь уже существует, попробуйте еще раз
        </div>
      )}
      <div className="form-block">
        <label className="input-label" htmlFor="name">
          Имя
        </label>
        <TextInput
          name="name"
          id="name"
          value={registerParams.name}
          onChange={(event) =>
            setRegisterParams({ ...registerParams, name: event.target.value })
          }
        />
      </div>
      <div className="form-block">
        <label className="input-label" htmlFor="login">
          Логин
        </label>
        <TextInput
          name="login"
          id="login"
          value={registerParams.login}
          onChange={(event) =>
            setRegisterParams({
              ...registerParams,
              login: event.target.value,
            })
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
          value={registerParams.password}
          onChange={(event) =>
            setRegisterParams({
              ...registerParams,
              password: event.target.value,
            })
          }
        />
      </div>
      <div className="form-block">
        <Button>Зарегистрироваться</Button>
      </div>
    </form>
  );
};

export default RegisterForm;
