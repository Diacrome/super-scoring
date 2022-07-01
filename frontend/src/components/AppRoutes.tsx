import React, { FC } from "react";
import Loader from "./Loader/Loader";
import { Route, Routes } from "react-router-dom";
import {
  notAuthorizedRoutes,
  passingRoutes,
  authorizedRoutes,
} from "../routes";
import { IRoutes } from "../types/routes";
import { useAppSelector } from "../hooks/useAppSelector";

const AppRoutes: FC = () => {
  const { loading, authorized, currentPass } = useAppSelector(
    (state) => state.status
  );

  const getRoutes = (routes: IRoutes) => {
    return routes.map((route) => (
      <Route path={route.path} element={route.element} />
    ));
  };

  if (loading) {
    return <Loader />;
  } else if (!authorized) {
    return <Routes>{getRoutes(notAuthorizedRoutes)}</Routes>;
  } else if (currentPass) {
    return <Routes>{getRoutes(passingRoutes)}</Routes>;
  }
  return <Routes>{getRoutes(authorizedRoutes)}</Routes>;
};

export default AppRoutes;
