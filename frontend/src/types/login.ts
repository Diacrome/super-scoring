export enum AuthMode {
  SignIn = "SignIn",
  SignUp = "SignUp",
}

export interface LoginParams {
  login: string;
  password: string;
}
