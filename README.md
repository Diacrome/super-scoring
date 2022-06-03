# super-scoring
HH school 2022 project
## Инструкция по работе
* Запуск приложения и БД `docker-compose up --build`
* Пересобрать и запустить приложение `docker-compose up -d --no-deps --build backend`
* Остановить `docker-compose down`
* Перезапустить всю систему с обновлённой базой данных: 
* Windows:  run.bat
* Linux:  run.sh
* run p  -  запуск контейнера только с базой данных postgres
* run b  -  запуск только backend части проекта
* run t  -  запуск backend части и её тестирование
* run    -  запуск всего что есть в проекте
* Порт работы backend: 8000
* Порт БД: 5444. Имя пользователя и пароль "hh"
* Порт доступа контента через браузер: 9999
* Для создания тестов импортировать коллекцию ./tests/tests.json
      в postman, добавить запросы, написать для них тесты.
      Важно формировать строку запроса из переменных коллекции.
      После завершения экспортировать коллекцию обратно в файл ./tests/tests.json
      и поставить в конце символ перевода строки
* Иструкция по составлению тестов, составленая специально для комманды: https://docs.google.com/document/d/1LYcL9fo5yuCZ1yCJtYXeNFw1VLT9_HBx/edit
* Сторонний гайд для начинающих работать с Postman: https://testengineer.ru/gajd-po-testirovaniyu-v-postman/
* Ещё полезная информация для создания тестов в Postman: https://medium.com/genesis-media/%D0%B8-%D0%B2%D1%81%D0%B5-%D0%B6%D0%B5-%D0%BA%D0%B0%D0%BA-%D1%82%D0%B5%D1%81%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D1%82%D1%8C-api-%D0%B2-postman-b02dd977da14
