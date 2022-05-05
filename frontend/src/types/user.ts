export interface UserState {
    isAuth: boolean;
}

export enum UserActionTypes {
    SIGN_IN = 'SIGN_IN',
    LOG_OUT = 'LOG_OUT'
}

export interface UserAction {
    type: UserActionTypes;
}
