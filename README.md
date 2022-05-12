# super-scoring

HH school 2022 project

## Инструкция по работе

* Запуск приложения и БД `docker-compose up --build`
* Пересобрать и запустить приложение `docker-compose up -d --no-deps --build backend`
* Остановить `docker-compose down`
* Перезапустить всю систему с обновлённой базой данных: 
                                 Windows:  run.bat
                                   Linux:  run.sh
* Порт работы backend: 8000
* Порт БД: 5444. Имя пользователя и пароль "hh"
* Порт доступа контента через браузер: 9999
