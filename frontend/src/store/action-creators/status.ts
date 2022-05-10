import {CurrentPass, StatusAction, StatusActionTypes} from "../../types/status";
import {Dispatch} from "redux";

export const fetchStatus = (currentPass = statusNotPass) => {
    return async (dispatch: Dispatch<StatusAction>) => {
        dispatch({type: StatusActionTypes.FETCH_STATUS});
        const authStatus = Boolean(localStorage.getItem('authorized'));
        const statusPayload = {
            authorized: authStatus,
            currentPass: currentPass
        };
        setTimeout(() => dispatch({type: StatusActionTypes.FETCH_STATUS_SUCCESS, payload: statusPayload}), 500);
    }
}

export const signIn = () => {
    return {type: StatusActionTypes.SIGN_IN}
}

export const logOut = () => {
    return {type: StatusActionTypes.LOG_OUT}
}

const statusNotPass: CurrentPass = null;

export const statusPass: CurrentPass = {
    answeredQuestions: {1: false},
    startTime: 123,
    testId: 1
}
