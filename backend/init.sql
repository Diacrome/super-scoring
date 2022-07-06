create table ss_user
(
    id       serial primary key,
    login    varchar(64)  not null,
    password varchar(128) not null,
    name     varchar(64)  not null,
    surname  varchar(64),
    patronymic varchar(64),
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
    qualification_score smallint not null,
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
       (3, 'Тест по автослесарному делу', 'Тест на знание профессии автослесаря', now(), now(), 300),
       (4, 'Проверь свой уровень английского','Тестирование профессионального английского языка',now(),now(),1800);

insert into qualification (test_id,qualification_score,qualification_name)
values (1,24,'Доктор математических наук'),
       (1,18,'Кандидат математических наук'),
       (1,12,'Преподаватель высшей математики'),
       (1,7,'Преподаватель арифметики в школе'),
       (1,4,'Выпускник школы'),
       (2,19,'Proficiency'),
       (2,16,'Advanced'),
       (2,12,'Upper-Intermediate'),
       (2,8,'Intermediate'),
       (2,4,'Pre-Intermediate'),
       (2,2,'Elementary'),
       (3,27,'6 разряд'),
       (3,23,'5 разряд'),
       (3,17,'4 разряд'),
       (3,12,'3 разряд'),
       (3,7,'2 разряд'),
       (3,4,'1 разряд'),
       (4,18,'Elementary'),
       (4,36,'Pre-Intermediate'),
       (4,54,'Intermediate'),
       (4,72,'Upper-Intermediate'),
       (4,90,'Advanced');


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
       (2, 'Jane Smith – cacti gardener. Many people are fond %answer1 gardening but Jane is different from them – she %answer2 only cacti. As a young girl she liked watching how her mother took care of plants, trees, and bushes in their garden and Jane helped her a lot. Jane’s collection of cacti from all over the world reminds her of her mother and childhood.', '{"answer1": {"1": "plants", "2": "is planting", "3": "has planted"}, "answer2": {"1": "plants", "2": "is planting", "3": "has planted"}}', '{"answer1": "2", "answer2": "3"}', null, 'MULTIPLE_QUESTIONS_SINGLE_CHOICE', 1, now(), now(), 30),
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
       (3, 'Топливная система: Топливный насос — это..', '{"1": "резервуар для хранения топлива, с которым может сообщаться система улавливания паров топлива", "2": "элемент, который управляет впрыском и обеспечивает необходимый состав топливно-воздушной смеси", "3": "устройство, которое обеспечивает очистку топлива от разнообразных загрязнений, пыли и посторонних твердых частиц", "4": "устройство, которое подает топливо из бака к двигателю и создает высокое давление"}', '{"answer": "4"}', null,'SINGLE_CHOICE', 1, now(), now(), 30),
       (4, 'If you match these criteria, contact your manager because you qualify for a salary %answer1\n\n -achieved your quarter goal\n -fulfilled personal development plan\n -received less than 5% of negative %answer2 from customers.\n\n A candidate for the salary increase is %answer3', '{"answer1": {"1": "upgrade", "2": "growth", "3": "raise"}, "answer2": {"1": "feedback", "2": "review", "3": "criticism"}, "answer3": {"1": "a team player", "2": "a goal-achiever who gives a positive experience to clients", "3": "an employee who fulfilled sales plan"}}', '{"answer1": "3", "answer2": "1", "answer3": "2"}', null,'MULTIPLE_QUESTIONS_SINGLE_CHOICE', 2, now(), now(), 30),
       (4, 'FROM NOW ON ALL DOCUMENTATION IS ON CORPORATE EMAILS\n A week ago, our company hired the 5000th employee to the staff. That means that we aren’t a small startup anymore, but a big company. It is time to take care of %answer1 and move all documentation from personal to corporate emails.\n Do not worry, there’s no need to move your old files to the corporate’s drive. Just make sure that from now on, every data you create is located on your work %answer2\n The company requires to keep all documentation on corporate emails from now on because %answer3', '{"answer1": {"1": "insurance", "2": "security", "3": "safety"}, "answer2": {"1": "profile", "2": "link", "3": "account"}, "answer3": {"1": "they have a lot of employees and want to ensure digital security", "2": "they don’t want to move old files from personal accounts", "3": "they lost important data"}}', '{"answer1": "2", "answer2": "3", "answer3": "1"}', null,'MULTIPLE_QUESTIONS_SINGLE_CHOICE', 4, now(), now(), 30),
       (4, 'Dear Mr. Smith,\n I got the letter you sent us about the telemarketing system. All conditions in the offer %answer1 great for us, just I want to double-check a few things with you. You said the system is up-to-date, but what will happen if it is outdated at some point? Do you provide a supporting service?\n Also, you mentioned that it takes two weeks to install the system. We are in a bit of a hurry, so is there %answer2 to finish the installation process for senior management in 2–3 days? \n Please, give me a call to discuss these matters. \n The purpose of this letter is %answer3', '{"answer1": {"1": "sound", "2": "ring", "3": "go"}, "answer2": {"1": "an opportunity", "2": "a circumstance", "3": "a possibility"}, "answer3": {"1": "to decline the offer", "2": "to sign a contract as soon as possible", "3": "to clarify some details about the offer"}}', '{"answer1": "1", "answer2": "3", "answer3": "3"}', null,'MULTIPLE_QUESTIONS_SINGLE_CHOICE', 6, now(), now(), 30),
       (4, 'Most senior leaders of large enterprises state they want to %answer1 their workforce back to offices after the pandemic. Companies’ executives believe that teams located together build a high-productive work culture.\n However, employees are keen to retain their work-from-home privileges. Some of the advantages are less commute stress, flexible schedules, and saving money. Clearly, to keep staff businesses need to be sensitive around bringing people back to the office and figure %answer2 how to combine both office time and work from home.\n To maintain employees at work and fulfill their own interests companies should %answer3', '{"answer1": {"1": "relocate", "2": "shift", "3": "carry"}, "answer2": {"1": "out", "2": "up", "3": "through"}, "answer3": {"1": "locate teams together to build a high-productive work culture", "2": "create some sort of hybrid model necessitating partly office presence", "3": "keep privileges for workforce such as full-time work from home"}}', '{"answer1": "2", "answer2": "1", "answer3": "2"}', null,'MULTIPLE_QUESTIONS_SINGLE_CHOICE', 8, now(), now(), 30),
       (4, 'Nowadays dynamic work environment %answer1 a robust leadership pipeline. Leaders are not necessarily born; employees can learn leadership behaviors by making decisions and fully participating in building community. The following key leadership %answer2 are most important for success:\n - high integrity\n - drive for results and vision\n - ability to inspire others\n Leadership is not synonymous with management. A manager implements strategies and finds solutions to problems. In contrast, the goal of a leader is to engage people in living the vision.\n One of the findings about the leadership in working environment is that %answer3', '{"answer1": {"1": "needs", "2": "demands", "3": "requires"}, "answer2": {"1": "characteristics", "2": "traits", "3": "features"}, "answer3": {"1": "the leadership skills can be developed by implementing strategies and finding solutions to problems", "2": "leadership is not the same as management; if you weren’t born as a leader, you couldn’t become one", "3": "to cope with rapid changes, businesses have a need for strong leaders"}}', '{"answer1": "3", "answer2": "2", "answer3": "3"}', null,'MULTIPLE_QUESTIONS_SINGLE_CHOICE', 10, now(), now(), 30),
       (4, '— Good morning! I %answer1 an appointment with Mr. Bauer.\n— What is %answer2 name, please?\n— It %answer3 Kate Barry.\n— %answer4 do you spell your last name?', '{"answer1": {"1": "has", "2": "having", "3": "have"}, "answer2": {"1": "your", "2": "you", "3": "you’re"}, "answer3": {"1": "is", "2": "are", "3": " "}, "answer4": {"1": "what", "2": "how", "3": "why"}}', '{"answer1": "3", "answer2": "1", "answer3": "1", "answer4": "2"}', null,'MULTIPLE_QUESTIONS_SINGLE_CHOICE', 2, now(), now(), 30),
       (4, '— Did you %answer1 your way here all right?\n— Yeah, your secretary sent very clear directions. %answer2 part was to get a security pass, actually.\n— Oh, really? I %answer3 to the security to see how we can make the process simpler for visitors. Would you like %answer4 tea or coffee before we start?', '{"answer1": {"1": "found", "2": "finding", "3": "find"}, "answer2": {"1": "harder", "2": "the hardest", "3": "the most hard"}, "answer3": {"1": "will talk", "2": "talk", "3": "am talking"}, "answer4": {"1": "some", "2": "any", "3": "something"}}', '{"answer1": "3", "answer2": "2", "answer3": "1", "answer4": "1"}', null,'MULTIPLE_QUESTIONS_SINGLE_CHOICE', 4, now(), now(), 30),
       (4, '— Why did you decide %answer1 into the nonprofit sector? \n — Well, I just wanted to do something meaningful. So, I %answer2 the building of a new village school for 6 months already. \n — And are you happy with %answer3 results of your work? \n — Yeah, we’re going ahead of schedule. The construction %answer4 to end two months early.', '{"answer1": {"1": "going", "2": "go", "3": "to go"}, "answer2": {"1": "have been supervising", "2": "supervised", "3": "was supervising"}, "answer3": {"1": "a", "2": " ", "3": "the"}, "answer4": {"1": "is expect", "2": "is expected", "3": "expects"}}', '{"answer1": "3", "answer2": "1", "answer3": "3", "answer4": "2"}', null,'MULTIPLE_QUESTIONS_SINGLE_CHOICE', 6, now(), now(), 30),
       (4, '— If I were the investor, I %answer1 a single penny to our startup after that presentation. \n — How could I know the memory stick would fail? \n — You %answer2 a backup with you. \n — Well, don’t you dare blame it all %answer3 me! You said you %answer4 a technical rehearsal, but the mike sounded funny like I’m Darth Vader or something.', '{"answer1": {"1": "won’t give", "2": "wouldn’t give", "3": "wouldn’t have given"}, "answer2": {"1": "must have brought", "2": "needn’t have brought", "3": "should have brought"}, "answer3": {"1": "at", "2": "on", "3": "with"}, "answer4": {"1": "had run", "2": "ran", "3": "have run"}}', '{"answer1": "2", "answer2": "3", "answer3": "2", "answer4": "1"}', null,'MULTIPLE_QUESTIONS_SINGLE_CHOICE', 8, now(), now(), 30),
       (4, '— After our latest marketing campaign, not only %answer1 a ton of negative comments, but also a few high-profile influencers made harsh public statements. \n — I wonder %answer2 somehow scale down the negative sentiment. I mean, if we had issued an apology straight away, it %answer3 such a serious toll on our online reputation. \n — Well, maybe. Anyways, the CEO %answer4 our official stand by tomorrow evening, and we’ll decide on how to handle the situation in these circumstances.', '{"answer1": {"1": "we got", "2": "got we", "3": "did we get"}, "answer2": {"1": "whether we could", "2": "could we", "3": "if could we"}, "answer3": {"1": "won’t have taken", "2": "wouldn’t have taken", "3": "wouldn’t take"}, "answer4": {"1": "will have issued", "2": "will have been issuing", "3": "have issued"}}', '{"answer1": "3", "answer2": "1", "answer3": "2", "answer4": "1"}', null,'MULTIPLE_QUESTIONS_SINGLE_CHOICE', 10, now(), now(), 30),
       (4, 'ПРОСЛУШАЙТЕ АУДИО ФАЙЛ - СЦЕНА: В ОФИСНОЙ ОБСТАНОВКЕ\nВыберите правильный ответ. Why doesn’t Dany know Jordan if they work for the same company?', '{"1": "Jordan is a new colleague from California", "2": "Jordan doesn’t work for this company. He came just to see the office", "3": "Jordan works in an office in a different city"}', '{"answer": "3"}', '[{ "url": "audio/1.mp3", "type": "audio" }]','SINGLE_CHOICE', 1, now(), now(), 30),
       (4, 'ПРОСЛУШАЙТЕ АУДИО ФАЙЛ - СЦЕНА: В ОФИСНОЙ ОБСТАНОВКЕ\nПредставьте себя на месте Джордана. Поддержите разговор: Do you want me to show you around?', '{"1": "Of course, I can show it to you. It’s not a secret", "2": "Sure, it would be interesting to see the place", "3": "Thank you, but I already have one"}', '{"answer": "2"}', '[{ "url": "audio/1.mp3", "type": "audio" }]','SINGLE_CHOICE', 1, now(), now(), 30),
       (4, 'ПРОСЛУШАЙТЕ АУДИО ФАЙЛ - СЦЕНА: ТЕЛЕФОННЫЙ ЗВОНОК\nВыберите правильный ответ. When are Dany and Jordan going to meet?', '{"1": "Not clear. Dany will tell him the time later", "2": "On Wednesday", "3": "In half an hour"}', '{"answer": "1"}', '[{ "url": "audio/2.mp3", "type": "audio" }]','SINGLE_CHOICE', 2, now(), now(), 30),
       (4, 'ПРОСЛУШАЙТЕ АУДИО ФАЙЛ - СЦЕНА: ТЕЛЕФОННЫЙ ЗВОНОК\nКоллега обратился с вопросом.Продолжите диалог: When are you available?', '{"1": "I don’t know. We’ll see", "2": "It’ll take a couple of minutes", "3": "I’ll check my timetable"}', '{"answer": "3"}', '[{ "url": "audio/2.mp3", "type": "audio" }]','SINGLE_CHOICE', 2, now(), now(), 30),
       (4, 'ПРОСЛУШАЙТЕ АУДИО ФАЙЛ - СЦЕНА: СОБЕСЕДОВАНИЕ: СИДЯТ ЗА СТОЛОМ\nВыберите правильный ответ. How does Dany feel about going abroad?', '{"1": "He doesn’t like leaving the USA at all", "2": "He is fine with travelling for work but doesn’t want to live in a different country", "3": "He agrees to travel only to Spain and Russia because he speaks those languages"}', '{"answer": "2"}', '[{ "url": "audio/3.mp3", "type": "audio" }]','SINGLE_CHOICE', 3, now(), now(), 30),
       (4, 'ПРОСЛУШАЙТЕ АУДИО ФАЙЛ - СЦЕНА: СОБЕСЕДОВАНИЕ: СИДЯТ ЗА СТОЛОМ\nК вам обратился коллега. Продолжите диалог: How do you feel about that?', '{"1": "I don’t feel very well", "2": "I don’t have any problem with that", "3": "Fine, thanks. What about you?"}', '{"answer": "2"}', '[{ "url": "audio/3.mp3", "type": "audio" }]','SINGLE_CHOICE', 3, now(), now(), 30),
       (4, 'ПРОСЛУШАЙТЕ АУДИО ФАЙЛ - СЦЕНА: ДЭНИ ЗА СТОЛОМ. ДЖОРДАН СТУЧИТСЯ, ЗАХОДИТ В ДВЕРЬ\nВыберите правильный ответ. What is Jordan’s request and why does he make it?', '{"1": "He wants a bigger salary because he has shown good results", "2": "He wants a position with greater responsibilities", "3": "He wants a bonus for staying with the company for so long"}', '{"answer": "1"}', '[{ "url": "audio/4.mp3", "type": "audio" }]','SINGLE_CHOICE', 4, now(), now(), 30),
       (4, 'ПРОСЛУШАЙТЕ АУДИО ФАЙЛ - СЦЕНА: ДЭНИ ЗА СТОЛОМ. ДЖОРДАН СТУЧИТСЯ, ЗАХОДИТ В ДВЕРЬ\nК вам обратился подчиненный. Ответьте ему на запрос: It’s time that my work was recognised', '{"1": "I know you are responsible for the mistake", "2": "We do value your input", "3": "We’re way past the deadline"}', '{"answer": "2"}', '[{ "url": "audio/4.mp3", "type": "audio" }]','SINGLE_CHOICE', 4, now(), now(), 30),
       (4, 'ПРОСЛУШАЙТЕ АУДИО ФАЙЛ - СЦЕНА: ПЕРЕГОВОРЫ, СИДЯТ ЗА СТОЛОМ\nВыберите правильный ответ. Why does Dany think an ad in a business magazine is a better idea?', '{"1": "Their target audience doesn’t watch TV", "2": "They don’t have expertise to make a commercial better than their competitors", "3": "Their budget is not enough for a TV-commercial"}', '{"answer": "3"}', '[{ "url": "audio/5.mp3", "type": "audio" }]','SINGLE_CHOICE', 5, now(), now(), 30),
       (4, 'ПРОСЛУШАЙТЕ АУДИО ФАЙЛ - СЦЕНА: ПЕРЕГОВОРЫ, СИДЯТ ЗА СТОЛОМ\nВы на совещании. Что скажете на предложение коллеги: We all need to let the steam off', '{"1": "I agree, let’s call it a day", "2": "Well, it’s not rocket science", "3": "Right, it’s time to get the ball rolling"}', '{"answer": "1"}', '[{ "url": "audio/5.mp3", "type": "audio" }]','SINGLE_CHOICE', 5, now(), now(), 30);


insert into question_distribution (test_id, weight, question_count)
values (1, 1, 5),
       (1, 2, 5),
       (2, 1, 10),
       (3, 1, 5),
       (3, 2, 5),
       (4, 1, 2),
       (4, 2, 4),
       (4, 3, 2),
       (4, 4, 4),
       (4, 5, 2),
       (4, 6, 2),
       (4, 8, 2),
       (4, 10, 2);


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
       (2,1,'2022-05-20 17:44:59.791','2022-05-20 23:04:17.645',5,'PASSED'),
       (1,1,'2022-05-20 17:44:59.791','2022-05-20 23:04:17.645',10,'PASSED');


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
