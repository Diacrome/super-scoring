import React from 'react';
import {useParams} from 'react-router-dom'
import {useAppDispatch} from "../hooks/useAppDispatch";
import {fetchStatus, statusPass} from "../store/action-creators/status";

const TestInfo = () => {
    const {testId} = useParams();
    const dispatch = useAppDispatch();

    const startTest = () => {
        dispatch(fetchStatus(statusPass))
    }


    return (
        (testId === '1' || testId === '2') ?
            <div>
                Информация о тесте
                <button onClick={startTest}>Начать тест</button>
            </div>
            :
            <div>Тест не существует</div>
    );
};

export default TestInfo;
