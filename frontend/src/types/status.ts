export interface StatusState  {
    loading: boolean,
    authorized: boolean
    currentPass?: CurrentPass
}

export type CurrentPass =
    null |
    {
        answeredQuestions: {
            [questionOrderNumber : string] : boolean
        },
        startTime: number,
        testId: number
    }


export enum StatusActionTypes {
    FETCH_STATUS = 'FETCH_STATUS',
    FETCH_STATUS_SUCCESS = 'FETCH_STATUS_SUCCESS',
    SIGN_IN = 'SIGN_IN',
    LOG_OUT = 'LOG_OUT'
}

export type StatusAction = FetchStatusAction | FetchStatusSuccessAction | SignInAction | LogOutAction

interface FetchStatusAction {
    type: StatusActionTypes.FETCH_STATUS
}

interface FetchStatusSuccessAction {
    type: StatusActionTypes.FETCH_STATUS_SUCCESS,
    payload: {
        authorized: boolean,
        currentPass: CurrentPass,
    }
}

interface SignInAction {
    type: StatusActionTypes.SIGN_IN
}

interface LogOutAction {
    type: StatusActionTypes.LOG_OUT
}


