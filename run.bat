@ECHO OFF
cd %~dp0
docker-compose down --volumes
IF "%~1"=="p"  GOTO postgres
IF "%~1"=="b"  GOTO backend
IF "%~1"=="t"  GOTO tests
docker-compose up --build
:postgres
docker-compose up --build -- postgres
exit
:backend
docker-compose up --build -- postgres backend
exit
:tests
docker-compose up --build -- postgres backend tests
exit