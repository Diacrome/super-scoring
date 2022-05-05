import {UserAction, UserActionTypes, UserState} from "../../types/user";

const initialState: UserState = {
    isAuth: true
}

export const userReducer = (state = initialState, action: UserAction): UserState => {
    switch (action.type) {
        case UserActionTypes.SIGN_IN:
            return {...state, isAuth: true};
        case UserActionTypes.LOG_OUT:
            return {...state, isAuth: false};
        default:
            return state;
    }
}
