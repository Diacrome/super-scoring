export interface StatusState {
  loading: boolean;
  authorized: boolean;
  currentPass: CurrentPass | null;
}

export interface CurrentPass {
  answeredQuestions: Record<string, boolean>;
  startTime: string;
  testId: number;
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
    currentPass: CurrentPass | null;
  };
}

interface SignInAction {
  type: StatusActionType.SignIn;
}

interface LogOutAction {
  type: StatusActionType.LogOut;
}
