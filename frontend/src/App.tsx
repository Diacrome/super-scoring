import React, { useEffect } from "react";
import { BrowserRouter } from "react-router-dom";
import { fetchStatus } from "./store/action-creators/status";
import { useAppDispatch } from "./hooks/useAppDispatch";
import "./styles/App.css";
import AppRoutes from "./components/AppRoutes";
import { useDispatch } from "react-redux";
import "./types/global.d.ts";

function App() {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(fetchStatus());
  }, []);

  return (
    <div className="App">
      <BrowserRouter>
        <AppRoutes />
      </BrowserRouter>
    </div>
  );
}

export default App;
