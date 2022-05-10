import React, {useEffect} from 'react';
import {BrowserRouter, Route, Routes, Navigate} from "react-router-dom";
import HomeScreen from "./pages/HomeScreen";
import TestScreen from "./pages/TestScreen";
import Results from "./pages/Results";
import LoginScreen from "./pages/LoginScreen";
import TestInfo from "./pages/TestInfo";
import Loader from "./components/Loader/Loader";
import {fetchStatus} from "./store/action-creators/status";
import {useAppSelector} from "./hooks/useAppSelector";
import {useAppDispatch} from "./hooks/useAppDispatch";
import './styles/App.css'

function App() {
    const status = useAppSelector(state => state.status);
    const dispatch = useAppDispatch();

    useEffect(() => {
        dispatch(fetchStatus());
    }, [])

    console.log(status.loading, status.authorized);

    return (
        <BrowserRouter>
            {
                status.loading ?
                    <Loader/>
                    : !status.authorized ?
                        <Routes>
                            <Route path="/auth" element={<LoginScreen/>}/>
                            <Route path="*" element={<Navigate replace to="/auth"/>}/>
                        </Routes>
                        : status.currentPass === null ?
                            <Routes>
                                <Route path="/" element={<HomeScreen/>}/>
                                <Route path="/:testId" element={<TestInfo/>}/>
                                <Route path="/:testId/:completionId" element={<Results/>}/>
                            </Routes>
                            :
                            <Routes>
                                <Route path="/:testId/:completionId" element={<Results/>}/>
                                <Route path="*" element={<TestScreen/>}/>
                            </Routes>
            }
        </BrowserRouter>
    );
}

export default App;
