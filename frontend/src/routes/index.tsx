import LoginScreen from "../pages/LoginScreen";
import { Navigate } from "react-router-dom";
import React from "react";
import HomeScreen from "../pages/HomeScreen";
import TestInfo from "../pages/TestInfo";
import Results from "../pages/Results";
import { IRoutes } from "../types/routes";
import TestScreen from "../pages/TestScreen";

export const notAuthorizedRoutes: IRoutes = [
  { path: "/auth", element: <LoginScreen /> },
  { path: "*", element: <Navigate replace to="/auth" /> },
];

export const authorizedRoutes: IRoutes = [
  { path: "/", element: <HomeScreen /> },
  { path: "/auth", element: <Navigate replace to="/" /> },
  { path: "/:testId", element: <TestInfo /> },
  { path: "/:testId/:completionId", element: <Results /> },
];

export const passingRoutes: IRoutes = [
  { path: "/:testId/:completionId", element: <Results /> },
  { path: "*", element: <TestScreen /> },
];
