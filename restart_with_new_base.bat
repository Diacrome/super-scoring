cd %~dp0
docker-compose down --volumes
docker-compose up --build
