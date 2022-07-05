export interface StatusState {
  loading: boolean;
  authorized: boolean;
  role: Role | null;
  currentPass: CurrentPass | null;
}

export enum Role {
  Admin = "ADMIN",
  User = "USER",
}

export interface CurrentPass {
  answeredQuestions: Record<string, boolean>;
  startTime: string;
  testId: number;
  testPassId: number;
  status: string;
}

export enum StatusActionType {
  FetchStatus = "FetchStatus",
  FetchStatusSuccess = "FetchStatusSuccess",
  SignIn = "SignIn",
  LogOut = "LogOut",
}

export type StatusAction =
  | FetchStatusAction
  | FetchStatusSuccessAction
  | SignInAction
  | LogOutAction;

interface FetchStatusAction {
  type: StatusActionType.FetchStatus;
}

interface FetchStatusSuccessAction {
  type: StatusActionType.FetchStatusSuccess;
  payload: {
    authorized: boolean;
    role: Role;
    currentPass: CurrentPass | null;
  };
}

interface SignInAction {
  type: StatusActionType.SignIn;
}

interface LogOutAction {
  type: StatusActionType.LogOut;
}
