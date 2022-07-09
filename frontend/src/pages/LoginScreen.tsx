import React, { FC, useState } from "react";
import { LoginMode } from "../types/login";
import AuthForm from "../components/AuthForm";
import RegisterForm from "../components/RegisterForm";

const LoginScreen: FC = () => {
  const [loginMode, setLoginMode] = useState<LoginMode>(LoginMode.Auth);

  const handleMode = () => {
    switch (loginMode) {
      case LoginMode.Auth:
        setLoginMode(LoginMode.Register);
        break;
      case LoginMode.Register:
        setLoginMode(LoginMode.Auth);
        break;
    }
  };

  switch (loginMode) {
    case LoginMode.Auth:
      return (
        <div className="login-screen">
          <AuthForm handleMode={handleMode} />
        </div>
      );
    case LoginMode.Register:
      return (
        <div className="login-screen">
          <RegisterForm handleMode={handleMode} />
        </div>
      );
  }
};

export default LoginScreen;
