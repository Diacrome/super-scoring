export interface AuthParams {
  login: string;
  password: string;
}

export interface RegisterParams {
  name: string;
  login: string;
  password: string;
}

export enum LoginMode {
  Auth = "Auth",
  Register = "Register",
}

export interface FormProps {
  handleMode: () => void;
}

export enum IsRegister {
  No = "No",
  Yes = "Yes",
  Bad = "Bad",
}
