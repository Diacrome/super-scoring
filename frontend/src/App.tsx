import React from 'react';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import HomePage from "./pages/HomePage";
import TestPage from "./pages/TestPage";
import ResultPage from "./pages/ResultPage";
import AuthPage from "./pages/AuthPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<HomePage/>}/>
                <Route path="/auth" element={<AuthPage/>}/>
                <Route path="/:testId" element={<TestPage/>}/>
                <Route path="/:testId/:completionId" element={<ResultPage/>}/>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
