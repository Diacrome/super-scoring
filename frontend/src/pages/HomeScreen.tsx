import React, { FC } from "react";
import { fetchStatus } from "../store/action-creators/status";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button/Button";
import { useAppDispatch } from "../hooks/useAppDispatch";
import { updateDefaultToken } from "../functions/updateDefaultToken";
import { useAppSelector } from "../hooks/useAppSelector";
import { Role } from "../types/status";
import { backendLocation } from "../types/locations";

const HomeScreen: FC = () => {
  const { role } = useAppSelector((state) => state.status);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const tests = [1, 2, 3];

  const logout = () => {
    localStorage.removeItem("Authorization");
    updateDefaultToken();
    dispatch(fetchStatus());
  };

  const goToAdminScreen = () => {
    const token = localStorage.getItem("Authorization");
    document.cookie = `Authorization=${token}`;
    window.location.href = `${backendLocation}/admin`;
  };

  return (
    <>
      <div className="navbar">
        <div className="navbar-buttons">
          {role === Role.Admin && (
            <div className="navbar-btn">
              <Button onClick={goToAdminScreen}>Админка</Button>
            </div>
          )}
          <div className="navbar-btn">
            <Button onClick={logout}>Выйти</Button>
          </div>
        </div>
      </div>
      <div className="test-catalog">
        {tests.map((testId) => (
          <div className="test-card" key={testId}>
            <Button key={testId} onClick={() => navigate("/" + testId)}>
              Тест {testId}
            </Button>
          </div>
        ))}
      </div>
    </>
  );
};

export default HomeScreen;
