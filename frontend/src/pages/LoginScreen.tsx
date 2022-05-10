import React, {FC} from 'react';
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router-dom";
import {signIn} from "../store/action-creators/status";

const LoginScreen: FC = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const auth = () => {
        localStorage.setItem('authorized', 'true');
        dispatch(signIn());
        navigate("/");
    }

    return (
        <div>
            <button onClick={auth}>
                Войти
            </button>
        </div>
    );
};

export default LoginScreen;
