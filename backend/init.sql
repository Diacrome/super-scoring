create table ss_user
(
    id       serial primary key,
    login    varchar(64)  not null,
    password varchar(128) not null,
    name     varchar(64)  not null,
    role     varchar(16)  not null
);

create table test
(
    id                serial primary key,
    creator_id        integer  not null references ss_user (id),
    name              text     not null,
    description       text,
    question_quantity smallint not null           default 10,
    attempt_quantity  smallint not null           default 3,
    repeat_interval   integer  not null           default 20,
    date_created      timestamp without time zone default (now() at time zone 'utc'),
    date_modified     timestamp without time zone,
    modifier_id       integer references  ss_user (id),
    is_active         boolean default true,
    time_limit        bigint
);

create table question_distribution
(
    id               serial primary key,
    test_id          integer not null references test (id),
    weight           smallint not null,
    question_count   integer not null
);

create table question
(
    id               serial primary key,
    test_id          integer not null references test (id),
    question_wording text    not null,
    payload          text    not null,
    answer           text    not null,
    question_content text,
    answer_type      varchar(32) not null,
    weight           smallint,
    date_created     timestamp without time zone default (now() at time zone 'utc'),
    date_modified    timestamp without time zone,
    time_limit       smallint,
    active           boolean not null default TRUE
);

create table test_pass
(
    id            serial primary key,
    test_id       integer not null references test (id),
    user_id       integer not null references ss_user (id),
    time_started  timestamp without time zone,
    time_finished timestamp without time zone,
    final_score smallint,
    status        varchar(10),
    max_possible   smallint,
    qualification_name  varchar(50)
);

create table qualification
(
    id           serial  primary key,
    test_id      integer not null references test (id),
    order_number smallint not null,
    qualification_name varchar(50)

);

create table answer
(
    id                  serial primary key,
    test_pass_id        integer  not null references test_pass (id),
    question_order      smallint not null,
    answer              text     not null,
    time_answer         timestamp without time zone not null
);

create table test_pass_question_id
(
    id                serial primary key,
    test_pass_id      integer  not null references test_pass (id),
    question_id_order smallint not null,
    question_id       integer  not null references question (id)

);


create table token
(
    id     serial     primary key,
    user_id      integer    not null references ss_user (id),
    token varchar(64) not null unique,
    expire_date  timestamp  without time zone
);




insert into ss_user (login, password, name, role)
values ('admin', 'LyB7wiGICF5mCQizydjYMA', 'Ivan', 'ADMIN'),-- pass: admin1
       ('adminLiza', 'Md62AUIObyA3rWFdP6xq8w', 'Liza', 'ADMIN'),  -- pass: admin2
       ('user1', 'EggTS90B9fvld1RemOBBdQ', 'Petr', 'USER'),  -- pass: user1
       ('user2', 'pfH0FU_RuP4PRaTB84uFqw', 'Anna', 'USER');   --  pass: user2


insert into test (creator_id, name, description, date_created, date_modified, time_limit)
values (1, 'Математический тест', 'Тест на знание таблицы умножения', now(), now(), 300),
       (2, 'Тест по английскому языку', 'Тест на знание английского языка', now(), now(), 300),
       (3, 'Тест по автослесарному делу', 'Тест на знание профессии автослесаря', now(), now(), 300);

insert into qualification (test_id,order_number,qualification_name)
values (1,1,'Доктор математических наук'),
       (1,2,'Кандидат математических наук'),
       (1,3,'Преподаватель высшей математики'),
       (1,4,'Преподаватель арифметики в школе'),
       (1,5,'Выпускник школы'),
       (2,1,'Proficiency'),
       (2,2,'Advanced'),
       (2,3,'Upper-Intermediate'),
       (2,4,'Intermediate'),
       (2,5,'Pre-Intermediate'),
       (2,6,'Elementary'),
       (3,1,'6 разряд'),
       (3,2,'5 разряд'),
       (3,3,'4 разряд'),
       (3,4,'3 разряд'),
       (3,5,'2 разряд'),
       (3,6,'1 разряд');

insert into question(test_id, question_wording, payload, answer, question_content, answer_type, weight, date_created, date_modified,
                     time_limit)
values (1, '1*1 = ', '{"1": 1, "2": 3, "3": 4, "4": 1}', '{"answer": "1"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (1, '2*2 = ', '{"1": 1, "2": 4, "3": 4, "4": 1}', '{"answer": "2"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (1, '3*3 = ', '{"1": 1, "2": 3, "3": 9, "4": 1}', '{"answer": "3"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (1, '4*4 = ', '{"1": 1, "2": 3, "3": 4, "4": 16}', '{"answer": "4"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (1, '5*5 = ', '{"1": 25, "2": 3, "3": 4, "4": 1}', '{"answer": "1"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (1, '6*6 = ', '{"1": 1, "2": 36, "3": 4, "4": 1}', '{"answer": "2"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (1, '7*7 = ', '{"1": 1, "2": 3, "3": 49, "4": 1}', '{"answer": "3"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (1, '8*8 = ', '{"1": 1, "2": 3, "3": 4, "4": 64}', '{"answer": "4"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (1, '9*9 = ', '{"1": 81, "2": 3, "3": 4, "4": 1}', '{"answer": "1"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (1, '10*10 = ', '{"1": 100, "2": 1000, "3": 4, "4": 1}', '{"answer": "1"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (1, '6*5 = ', '{"1": 1, "2": 30, "3": 35, "4": 1}', '{"answer": "2"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (1, '7*6 = ', '{"1": 1, "2": 3, "3": 42, "4": 44}', '{"answer": "3"}', null,'SINGLE_CHOICE', 2, now(), now(), 30),
       (1, '7*8 = ', '{"1": 1, "2": 3, "3": 4, "4": 56}', '{"answer": "4"}', null,'SINGLE_CHOICE', 2, now(), now(), 30),
       (1, '8*9 = ', '{"1": 72, "2": 3, "3": 4, "4": 1}', '{"answer": "1"}', null,'SINGLE_CHOICE', 2, now(), now(), 30),
       (1, '6*4 = ', '{"1": 1, "2": 24, "3": 4, "4": 1}', '{"answer": "2"}', null,'SINGLE_CHOICE', 2, now(), now(), 30),
       (1, '2*3 = ', '{"1": 1, "2": 3, "3": 6, "4": 1}', '{"answer": "3"}', null,'SINGLE_CHOICE', 2, now(), now(), 30),
       (1, '5*4 = ', '{"1": 1, "2": 3, "3": 4, "4": 20}', '{"answer": "4"}', null,'SINGLE_CHOICE', 2, now(), now(), 30),
       (1, '5*4 = ', '{"1": 1, "2": 20, "3": 4, "4": 20}', '{"multiple_answer1": "2", "multiple_answer2": "4"}', null,'MULTIPLE_CHOICE', 1, now(), now(), 30),
       (2, 'Kang thought the spicy tofu dish was %answer tasty, so he ordered it again.', '{"1": "mainly", "2": "fairy", "3": "especially", "4": "slightly"}', '{"answer": "3"}', null, 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'They’ve been married for over fifty years, but she still remembers the day she first %answer.', '{"1": "keen on him", "2": "stuck on him", "3": "fell for him", "4": "wed him"}', '{"answer": "3"}', null, 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'She arrived at 8 p.m., opened the door and shouted “Good %answer!”', '{"1": "morning", "2": "evening", "3": "night", "4": "bye"}', '{"answer": "2"}', null, 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'It %answer that our Earth is so safe and peaceful planet, doesn''t it?', '{"1": "seems", "2": "is seeming", "3": "seemed"}', '{"answer": "1"}', null, 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'If I had had a lot of money yesterday, I would a car now.', '{"1": "have bought", "2": "buy", "3": "bought"}', '{"answer": "1"}', null, 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'For the questions below, please choose the best option to complete the sentence or conversation. \n Can I park here?', '{"1": "Sorry, I did that.", "2": "It''s the same place.", "3": "Only for half an hour."}', '{"answer": "3"}', null, 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, ' I''m sorry - I didn''t %answer to disturb you.', '{"1": "hope", "2": "think", "3": "mean", "4": "suppose"}', '{"answer": "3"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, '%answer tired Melissa is when she gets home from work, she always makes time to say goodnight to the children.', '{"1": "Whatever", "2": "No matter how", "3": "However much", "4": "Although"}', '{"answer": "2"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'How old is your %answer?” – “She''s thirteen.”', '{"1": "brother", "2": "son", "3": "boyfriend", "4": "sister"}', '{"answer": "4"}', '[{ "url": "image/smile.png", "type": "image" }]','SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'Do you like horror movies?', '{"1": "Yes, I am", "2": "Yes, I like", "3": "Yes, I do", "4": "Yes, it is"}', '{"answer": "3"}', '[{ "url": "image/balloon.png", "type": "image" }]','SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'Jane Smith – cacti gardener. Many people are fond %answer1 gardening but Jane is different from them – she %answer2 only cacti. As a young girl she liked watching how her mother took care of plants, trees, and bushes in their garden and Jane helped her a lot. Jane’s collection of cacti from all over the world reminds her of her mother and childhood.', '{"answer1":{"1": "plants", "2": "is planting", "3": "has planted"}, "answer2": {"1": "plants", "2": "is planting", "3": "has planted"}}', '{"answer1": "2", "answer2" : "3"}', null, 'MULTIPLE_QUESTIONS_SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'Что произносится на видео?', '{"1": "Это мои предложения", "2": "Это мой офис", "3": "Это в моём офисе", "4": "Я у себя в офисе"}', '{"answer": "2"}', '[{ "url": "video/6246068703.mp4", "type": "video" }]', 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'Everybody hold on %answer the tree.  (нужно понять, что произносят на видео)', '{"1": "in", "2": "out", "3": "to", "4": "at"}','{"answer": "3"}', '[{ "url": "video/09a67c515c.mp4", "type": "video" }]', 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'Что произносится на видео?', '{"1": "Когда это?", "2": "Где это?", "3": "Что это?", "4": "Откуда это?"}', '{"answer": "2"}', '[{ "url": "video/48162aaadc.mp4", "type": "video" }]', 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'Какое высказывание соответствует озвученной информации?', '{"1": "Prices in Ikea depend on what people purchase more often", "2": "Ikea offers a wide range of products", "3": "Ikea mostly sells affordable goods", "4": "Ikea’s furniture is out of most people’s price range"}', '{"answer": "3"}', '[{ "url": "video/c37e7077c0.mp4", "type": "video" }]', 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'Вставьте правильный артикль, если он необходим:', '{"1": "an", "2": "–", "3": "a", "4": "the"}', '{"answer": "4"}', '[{ "url": "image/submorina.png", "type": "image" }]', 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'When do you play tennis? - %answer Fridays.', '{"1": "On", "2": "By", "3": "In", "4": "At"}', '{"answer": "1"}', '[{ "url": "image/racket.png", "type": "image" }]', 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'Выберите правильный вариант:', '{"1": "don''t, understands", "2": "don''t, understand", "3": "doesn''t, understand", "4": "doesn''t, understands"}', '{"answer": "4"}', '[{ "url": "image/coffee.png", "type": "image" }]', 'SINGLE_CHOICE', 1, now(), now(), 30),
       (2, 'Выберите правильный вариант перевода: \n Ему следует убрать свою комнату', '{"1": "He should clean up his room", "2": "He needs to remove his room", "3": "He must clean up his room", "4": "He has to remove his room"}', '{"answer": "1"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (3, 'Отопление: О наличии протечки в системе отопления могут сигнализировать:', '{"1": "неработающий отопитель салона транспортного средства", "2": "лужицы охлаждающей жидкости", "3": "ошибка запуска жидкостного подогревателя", "4": "характерный запах в салоне", "5": "непривычные шумы в работе штатного насоса"}', '{"multiple_answer1": "2", "multiple_answer2": "4"}', null,'MULTIPLE_CHOICE', 2, now(), now(), 30),
       (3, 'Отопление: Какое устройство системы отопления может работать автономно при неработающем двигателе?', '{"1": "Водяной насос", "2": "Конвекторный отопитель", "3": "Электровентилятор", "4": "Предпусковой подогреватель"}', '{"answer": "4"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (3, 'Кондиционеры: Заполните пробел: в устройстве кондиционера можно выделить %answer контура/контуров давлени', '{"1": 2, "2": 3, "3": 4, "4": 5}', '{"answer": "1"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (3, 'Кондиционеры: Определите последовательность заправочных работ с кондиционером при помощи заправочной станции', '{"1": "Закачать фреон в систему", "2": "Вакуумизировать систему", "3": "Откачать фреон и сторонние жидкости", "4": "Задать программу"}', '{"1": "4", "2": "3", "3": "2", "4": "1"}', null,'RANKING', 2, now(), now(), 30),
       (3, 'Тормозная система: Гидравлический привод тормозной системы может включать в себя:', '{"1": "тормозные ленты", "2": "усилитель тормозов", "3": "тормозную педаль", "4": "тормозной барабан", "5": "соединительные шланги и трубопроводы"}', '{"multiple_answer1": "2", "multiple_answer2": "3", "multiple_answer3": "5"}', null,'MULTIPLE_CHOICE', 2, now(), now(), 30),
       (3, 'Тормозная система: Тормозной суппорт', '{"1": "преобразовывает гидравлическое давление жидкости из главного тормозного цилиндра, в механическое усилие, действующее на тормозные колодки", "2": "создает дополнительное усилие на педали тормоза за счет разряжения и облегчает работу тормозной системы автомобиля", "3": "передает прикладываемое усилие от ноги водителя на главный тормозной цилиндр", "4": "вытесняет жидкость в трубопроводы и колесные цилиндры"}', '{"answer": "1"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (3, 'Силовой агрегат, КПП, карданный вал: Правильными действиями при демонтаже силового агрегата являются:', '{"1": "зафиксировать брызговик двигателя", "2": "отсоединить провода от клеммы аккумуляторной батареи", "3": "закрепить привод спидометра", "4": "слить жидкость из системы охлаждения", "5": "слить масло коробки передач"}', '{"multiple_answer1": "2", "multiple_answer2": "4", "multiple_answer3": "5"}', null,'MULTIPLE_CHOICE', 2, now(), now(), 30),
       (3, 'Силовой агрегат, КПП, карданный вал: Клиноременной вариатор является деталью', '{"1": "гидромеханической автоматической коробки передач", "2": "бесступенчатой коробки передач", "3": "роботизированной коробки передач", "4": "механической коробки передач"}', '{"answer": "2"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (3, 'Двигатель: Расставьте такты рабочего цикла двигателя внутреннего сгорания в правильном порядке', '{"1": "Рабочий ход (расширение)", "2": "Впуск", "3": "Выпуск", "4": "Сжатие"}', '{"1": "2", "2": "4", "3": "1", "4": "2"}', null,'RANKING', 2, now(), now(), 30),
       (3, 'Двигатель: На первоначальном этапе запуска поршневого двигателя внутреннего сгорания происходит', '{"1": "зажигание свечи", "2": "движение поршня", "3": "раскрутка маховика стартером", "4": "открытие впускного клапана"}', '{"answer": "3"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (3, 'Мосты и подвески: Какой тип подвески представлен на изображении?', '{"1": "трехрычажная подвеска с поперечным расположением рычагов", "2": "двухрычажная подвеска с продольным расположением рычагов", "3": "однорычажная подвеска с продольным рычагом", "4": "двухрычажная подвеска с поперечным расположением рычагов"}', '{"answer": "4"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (3, 'Мосты и подвески: В основе конструкции амортизаторов могут использоваться', '{"1": "муфты с резиновыми упругими элементами", "2": "металлические пружины", "3": "пластины из полимеров с различными добавками", "4": "гидравлические устройства", "5": "пневматические баллоны"}', '{"multiple_answer1": "1", "multiple_answer2": "2", "multiple_answer3": "4", "multiple_answer4": "5"}', null,'MULTIPLE_CHOICE', 2, now(), now(), 30),
       (3, 'Рулевое управление: При каком максимальном значении суммарного люфта в рулевом управлении допускается эксплуатация легкового автомобиля и микроавтобуса?', '{"1": "15°", "2": "10°", "3": "20°", "4": "5°"}', '{"answer": "2"}', null,'SINGLE_CHOICE', 2, now(), now(), 30),
       (3, 'Рулевое управление: Оцените корректность утверждений:\nЧастыми поломками гидроусилителя руля могут выступать следующие:\nА) Течь шланга гидроусилителя\nБ) Течь рабочей жидкости из мест соединений', '{"1": "Вариант А верный, Б — неверный", "2": "Вариант Б верный, А — неверный", "3": "А и Б неверны", "4": "А и Б верны"}', '{"answer": "4"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (3, 'Электрика: Оцените корректность утверждений:\nТермоусадочная изоляция применяется при электроремонте:\nА) для изоляции токоведущих частей\nБ) для защиты от окисления кислородом', '{"1": "Вариант Б верный, А неверный", "2": "А и Б неверны", "3": "А и Б верны", "4": "Вариант А верный, Б неверный"}', '{"answer": "3"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (3, 'Электрика: В чем заключаются особенности измерения величины тока мультиметром?', '{"1": "Выключатель измерительного прибора прерывает электрическую цепь", "2": "Мультиметр используется в качестве амперметра", "3": "Для предотвращения разрушения прибора от перегрузок может использоваться предохранитель ", "4": "Мультиметр подключается параллельно в электрическую цепь"}', '{"multiple_answer1": "2", "multiple_answer2": "3"}', null,'MULTIPLE_CHOICE', 2, now(), now(), 30),
       (3, 'Топливная система: Оцените корректность утверждений:\nА) Инжекторные системы подачи топлива, по сравнению с карбюраторными,  более точно дозируют топливо и обеспечивают более экономный его расход\nБ) В устройстве инжекторного двигателя с распределенным впрыском имеется одна форсунка, подающая  топливо для всех цилиндров двигателя', '{"1": "Вариант А верный, Б — неверный", "2": "Вариант А неверный, Б — верный", "3": "А и Б верны", "4": "А и Б неверны"}', '{"answer": "1"}', null,'SINGLE_CHOICE', 2, now(), now(), 30),
       (3, 'Топливная система: Топливный насос — это..', '{"1": "резервуар для хранения топлива, с которым может сообщаться система улавливания паров топлива", "2": "элемент, который управляет впрыском и обеспечивает необходимый состав топливно-воздушной смеси", "3": "устройство, которое обеспечивает очистку топлива от разнообразных загрязнений, пыли и посторонних твердых частиц", "4": "устройство, которое подает топливо из бака к двигателю и создает высокое давление"}', '{"answer": "4"}', null,'SINGLE_CHOICE', 1, now(), now(), 30);

insert into question_distribution (test_id, weight, question_count)
values (1, 1, 5),
       (1, 2, 5),
       (2, 1, 10),
       (3, 1, 5),
       (3, 2, 5);

insert into test_pass (test_id, user_id, time_started, time_finished, final_score, status)
values (1, 3, now() - interval '100 seconds', now(), 6,'PASSED'),
       (1, 4, now() - interval '200 seconds', null, null,'PASS'),
       (2, 1, now() - interval '200 seconds', null, null,'PASS'),
       (2, 4, now() - interval '200 seconds', now(), 10,'PASSED'),
       (1, 3, now() - interval '100 seconds', now(), 7,'PASSED'),
       (2,3,'2022-05-20 17:46:39.791','2022-05-20 17:48:19.791',6,'PASSED'),
       (1,4,'2022-05-20 17:44:59.791','2022-05-20 17:48:19.791',10,'PASSED'),
       (1,3,'2022-05-20 17:46:39.791','2022-05-20 17:48:19.791',7,'PASSED'),
       (1,4,'2022-05-20 17:44:59.791','2022-05-20 23:02:35.601',8,'PASSED'),
       (1,4,'2022-05-20 18:03:21.564','2022-05-20 23:03:35.265',3,'PASSED'),
       (2,1,'2022-05-20 17:44:59.791','2022-05-20 23:04:17.645',5,'PASSED');


insert into test_pass_question_id (test_pass_id, question_id_order, question_id)
values (1, 1, 2),
       (1, 2, 12),
       (1, 3, 13),
       (1, 4, 14),
       (1, 5, 15),
       (1, 6, 7),
       (1, 7, 6),
       (1, 8, 5),
       (1, 9, 4),
       (1, 10, 3),
       (2, 1, 1),
       (2, 2, 3),
       (2, 3, 4),
       (2, 4, 6),
       (2, 5, 7),
       (2, 6, 10),
       (2, 7, 11),
       (2, 8, 13),
       (2, 9, 15),
       (2, 10, 17),
       (3, 1, 19),
       (3, 2, 20),
       (3, 3, 21),
       (3, 4, 22),
       (3, 5, 23),
       (3, 6, 24),
       (3, 7, 25),
       (3, 8, 26),
       (3, 9, 27),
       (3, 10, 28);


insert into answer (test_pass_id, question_order,  answer, time_answer)
values
    (1, 1, '{"answer": "1"}', now()),
    (1, 2, '{"answer": "1"}', now()),
    (1, 3, '{"answer": "1"}', now()),
    (1, 4, '{"answer": "1"}', now()),
    (1, 5, '{"answer": "1"}', now()),
    (1, 6, '{"answer": "1"}', now()),
    (1, 7, '{"answer": "1"}', now()),
    (1, 8, '{"answer": "1"}', now()),
    (1, 9, '{"answer": "1"}', now()),
    (1, 10, '{"answer": "1"}', now()),
    (2, 1, '{"answer": "1"}', now()),
    (2, 2, '{"answer": "1"}', now()),
    (2, 3, '{"answer": "1"}', now()),
    (2, 4, '{"answer": "1"}', now()),
    (2, 5, '{"answer": "1"}', now()),
    (2, 6, '{"answer": "1"}', now()),
    (2, 7, '{"answer": "1"}', now()),
    (2, 8, '{"answer": "1"}', now()),
    (2, 9, '{"answer": "1"}', now()),
    (2, 10, '{"answer": "1"}', now()),
    (3, 1, '{"answer": "1"}', now()),
    (3, 2, '{"answer": "1"}', now()),
    (3, 3, '{"answer": "1"}', now()),
    (3, 4, '{"answer": "1"}', now()),
    (3, 5, '{"answer": "1"}', now()),
    (3, 6, '{"answer": "1"}', now()),
    (3, 7, '{"answer": "1"}', now()),
    (3, 8, '{"answer": "1"}', now()),
    (3, 9, '{"answer": "1"}', now()),
    (3, 10, '{"answer1": "1", "answer2" :  "2"}', now());


insert into token (user_id, token, expire_date)
values (1, '557sa7', now()+interval'3d'),
       (3, 'sdf543', now()+interval'3d'),
       (4, 'fs952j', now()+interval'3d');
