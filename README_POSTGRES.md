#Запуск базы данных
1. Установить docker
2. Запустить docker daemon
3. Запустить контейнер командой `docker-compose up -d`
4. Результатом будет БД на порте 5444, имя пользователя и пароль "hh"
5. Подключиться к базе можно несколькими способами:\
    **Способ 1**: В командной строке написать: docker exec -it *docker_container_name*(что-то типа super-scoring_postgres_1) psql -U hh\
    **Способ 2**: В командной строке написать: docker exec -it *docker_container_name*(что-то типа super-scoring_postgres_1) \bin\bash и уже в там можно вызвать psql-U hh\
    **Способ 3**: Если у вас есть postgres на компьютере то можно написать psql -U hh -p выбранный_вами_порт\
    **Способ 4**: Через pgadmin: Выбрать создать сервер, выбрать любое имя и указать\
        *host*: localhost\
        *port*: 5444\
        *Maintenance*: hh\
        *user*: hh\
        *pass*: hh\
Тестовая таблица будет лежать в Databases/hh/Schemas/public/Tests\
    **Способ 5**: Можно через IDEA во вкладке databases, выбрав PostgreSQL и указавав параметры такие же, как в пункте выше. Не знаю доступно ли это в IDEA community edition.\
    **Способ 6**: jdbc:postgresql://localhost:*5444*/postgres (вместо 5444 нужно указать свой порт) Это если хотите через Connection.
