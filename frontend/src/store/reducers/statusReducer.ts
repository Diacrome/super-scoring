import {StatusAction, StatusActionTypes, StatusState} from "../../types/status";

const authLocalStorage = Boolean(localStorage.getItem('authorized'));

const initialState: StatusState = {
    loading: true,
    authorized: authLocalStorage
}

export const statusReducer = (state = initialState, action: StatusAction): StatusState => {
    switch (action.type) {
        case StatusActionTypes.FETCH_STATUS:
            return {...state, loading: true};
        case StatusActionTypes.FETCH_STATUS_SUCCESS:
            return {...state, loading: false, authorized: action.payload.authorized, currentPass: action.payload.currentPass};
        case StatusActionTypes.SIGN_IN:
            return {...state, authorized: true};
        case StatusActionTypes.LOG_OUT:
            return {...state, authorized: false};
        default:
            return state;
    }
}
