import React, { FC, useEffect, useState } from "react";
import { fetchStatus } from "../store/action-creators/status";
import Button from "../components/Button/Button";
import { useAppDispatch } from "../hooks/useAppDispatch";
import { updateDefaultToken } from "../functions/updateDefaultToken";
import { useAppSelector } from "../hooks/useAppSelector";
import { Role } from "../types/status";
import { backendLocation } from "../types/locations";
import { fetchData } from "../functions/fetchData";
import { TestCatalog, TestCatalogInfo } from "../types/testCatalog";
import Loader from "../components/Loader/Loader";
import TestCardComponent from "../components/TestCardComponent";

const HomeScreen: FC = () => {
  const { role } = useAppSelector((state) => state.status);
  const dispatch = useAppDispatch();
  const [testCatalog, setTestCatalog] = useState<TestCatalog | null>(null);

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

  useEffect(() => {
    fetchData("test/all-tests-for-user").then(
      (testCatalogInfo: TestCatalogInfo) =>
        setTestCatalog(testCatalogInfo.testMap)
    );
  }, []);

  if (testCatalog === null) {
    return <Loader />;
  }

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
        {Object.values(testCatalog).map((testCard) => (
          <TestCardComponent
            key={testCard.id}
            id={testCard.id}
            name={testCard.name}
          />
        ))}
      </div>
    </>
  );
};

export default HomeScreen;
