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
    date_created      timestamp without time zone default (now() at time zone 'utc'),
    date_modified     timestamp without time zone,
    modifier_id       integer references  ss_user (id),
    is_active         boolean default true
);

create table question
(
    id               serial primary key,
    test_id          integer not null references test (id),
    question_wording text    not null,
    payload          json    not null,
    answer           json    not null,
    question_content text,
    date_created     timestamp without time zone default (now() at time zone 'utc'),
    date_modified    timestamp without time zone,
    time_limit       smallint
);

create table test_pass
(
    id            serial primary key,
    test_id       integer not null references test (id),
    user_id       integer not null references ss_user (id),
    time_started  timestamp without time zone,
    time_finished timestamp without time zone,
    final_score smallint
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


insert into test (creator_id, name, description, date_created, date_modified)
values (1, 'Математический тест', 'Тест на знание таблицы умножения', now(), now()),
       (2, 'Тест по английскому языку', 'Тест на знание английского языка', now(), now());

insert into question(test_id, question_wording, payload, answer, question_content, date_created, date_modified,
                     time_limit)
values (1, '1*1 = ', '{"1": 1, "2": 3, "3": 4, "4": 1}', '{"answer": "1"}', null, now(), now(), 30),
       (1, '2*2 = ', '{"1": 1, "2": 4, "3": 4, "4": 1}', '{"answer": "2"}', null, now(), now(), 30),
       (1, '3*3 = ', '{"1": 1, "2": 3, "3": 9, "4": 1}', '{"answer": "3"}', null, now(), now(), 30),
       (1, '4*4 = ', '{"1": 1, "2": 3, "3": 4, "4": 16}', '{"answer": "4"}', null, now(), now(), 30),
       (1, '5*5 = ', '{"1": 25, "2": 3, "3": 4, "4": 1}', '{"answer": "1"}', null, now(), now(), 30),
       (1, '6*6 = ', '{"1": 1, "2": 36, "3": 4, "4": 1}', '{"answer": "2"}', null, now(), now(), 30),
       (1, '7*7 = ', '{"1": 1, "2": 3, "3": 49, "4": 1}', '{"answer": "3"}', null, now(), now(), 30),
       (1, '8*8 = ', '{"1": 1, "2": 3, "3": 4, "4": 64}', '{"answer": "4"}', null, now(), now(), 30),
       (1, '9*9 = ', '{"1": 81, "2": 3, "3": 4, "4": 1}', '{"answer": "1"}', null, now(), now(), 30),
       (1, '10*10 = ', '{"1": 100, "2": 1000, "3": 4, "4": 1}', '{"answer": "1"}', null, now(), now(), 30),
       (1, '6*5 = ', '{"1": 1, "2": 30, "3": 35, "4": 1}', '{"answer": "2"}', null, now(), now(), 30),
       (1, '7*6 = ', '{"1": 1, "2": 3, "3": 42, "4": 44}', '{"answer": "3"}', null, now(), now(), 30),
       (1, '7*8 = ', '{"1": 1, "2": 3, "3": 4, "4": 56}', '{"answer": "4"}', null, now(), now(), 30),
       (1, '8*9 = ', '{"1": 72, "2": 3, "3": 4, "4": 1}', '{"answer": "1"}', null, now(), now(), 30),
       (1, '6*4 = ', '{"1": 1, "2": 24, "3": 4, "4": 1}', '{"answer": "2"}', null, now(), now(), 30),
       (1, '2*3 = ', '{"1": 1, "2": 3, "3": 6, "4": 1}', '{"answer": "3"}', null, now(), now(), 30),
       (1, '5*4 = ', '{"1": 1, "2": 3, "3": 4, "4": 20}', '{"answer": "4"}', null, now(), now(), 30),
       (2, 'Kang thought the spicy tofu dish was %answer tasty, so he ordered it again.', '{"1": "mainly", "2": "fairy", "3": "especially", "4": "slightly"}', '{"answer": "3"}', null, now(), now(), 30),
       (2, 'They’ve been married for over fifty years, but she still remembers the day she first %answer.', '{"1": "keen on him", "2": "stuck on him", "3": "fell for him", "4": "wed him"}', '{"answer": "3"}', null, now(), now(), 30),
       (2, 'She arrived at 8 p.m., opened the door and shouted “Good %answer!”', '{"1": "morning", "2": "evening", "3": "night", "4": "bye"}', '{"answer": "2"}', null, now(), now(), 30),
       (2, 'It %answer that our Earth is so safe and peaceful planet, doesn''t it?', '{"1": "seems", "2": "is seeming", "3": "seemed"}', '{"answer": "1"}', null, now(), now(), 30),
       (2, 'If I had had a lot of money yesterday, I would a car now.', '{"1": "have bought", "2": "buy", "3": "bought"}', '{"answer": "1"}', null, now(), now(), 30),
       (2, 'For the questions below, please choose the best option to complete the sentence or conversation. \n Can I park here?', '{"1": "Sorry, I did that.", "2": "It''s the same place.", "3": "Only for half an hour."}', '{"answer": "3"}', null, now(), now(), 30),
       (2, ' I''m sorry - I didn''t %answer to disturb you.', '{"1": "hope", "2": "think", "3": "mean", "4": "suppose"}', '{"answer": "3"}', null, now(), now(), 30),
       (2, '%answer tired Melissa is when she gets home from work, she always makes time to say goodnight to the children.', '{"1": "Whatever", "2": "No matter how", "3": "However much", "4": "Although"}', '{"answer": "2"}', null, now(), now(), 30),
       (2, 'How old is your %answer?” – “She''s thirteen.”', '{"1": "brother", "2": "son", "3": "boyfriend", "4": "sister"}', '{"answer": "4"}', null, now(), now(), 30),
       (2, 'Do you like horror movies?', '{"1": "Yes, I am", "2": "Yes, I like", "3": "Yes, I do", "4": "Yes, it is"}', '{"answer": "3"}', null, now(), now(), 30),
       (2, 'Jane Smith – cacti gardener. Many people are fond %answer1 gardening but Jane is different from them – she %answer2 only cacti. As a young girl she liked watching how her mother took care of plants, trees, and bushes in their garden and Jane helped her a lot. Jane’s collection of cacti from all over the world reminds her of her mother and childhood.', '{"answer1":{"1": "plants", "2": "is planting", "3": "has planted"}, "answer2": {"1": "plants", "2": "is planting", "3": "has planted"}}', '{"answer1": "2", "answer2" : "3"}', null, now(), now(), 30);


insert into test_pass (test_id, user_id, time_started, time_finished, final_score)
values (1, 3, now() - interval '100 seconds', now(), 6),
       (1, 4, now() - interval '200 seconds', null, null),
       (1, 1, now() - interval '200 seconds', null, null),
       (2, 4, now() - interval '200 seconds', now(), 10),
       (1, 3, now() - interval '100 seconds', now(), 7),
       (2,3,'2022-05-20 17:46:39.791','2022-05-20 17:48:19.791',6),
       (1,4,'2022-05-20 17:44:59.791','2022-05-20 17:48:19.791',10),
       (1,3,'2022-05-20 17:46:39.791','2022-05-20 17:48:19.791',7),
       (1,4,'2022-05-20 17:44:59.791','2022-05-20 23:02:35.601',8),
       (1,4,'2022-05-20 18:03:21.564','2022-05-20 23:03:35.265',3),
       (2,1,'2022-05-20 17:44:59.791','2022-05-20 23:04:17.645',5),
       (1,2,'2022-05-20 18:03:45.554',NULL,NULL);


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
       (3, 1, 18),
       (3, 2, 19),
       (3, 3, 20),
       (3, 4, 21),
       (3, 5, 23),
       (3, 6, 24),
       (3, 7, 25),
       (3, 8, 26),
       (3, 9, 27),
       (3, 10, 28);


insert into answer (test_pass_id, question_order,  answer, time_answer)
values
    (1, 0, '{"answer": "1"}', now()),
    (1, 1, '{"answer": "1"}', now()),
    (1, 2, '{"answer": "1"}', now()),
    (1, 3, '{"answer": "1"}', now()),
    (1, 4, '{"answer": "1"}', now()),
    (1, 5, '{"answer": "1"}', now()),
    (1, 6, '{"answer": "1"}', now()),
    (1, 7, '{"answer": "1"}', now()),
    (1, 8, '{"answer": "1"}', now()),
    (1, 9, '{"answer": "1"}', now()),
    (2, 0, '{"answer": "1"}', now()),
    (2, 1, '{"answer": "1"}', now()),
    (2, 2, '{"answer": "1"}', now()),
    (2, 3, '{"answer": "1"}', now()),
    (2, 4, '{"answer": "1"}', now()),
    (2, 5, '{"answer": "1"}', now()),
    (2, 6, '{"answer": "1"}', now()),
    (2, 7, '{"answer": "1"}', now()),
    (2, 8, '{"answer": "1"}', now()),
    (2, 9, '{"answer": "1"}', now()),
    (3, 0, '{"answer": "1"}', now()),
    (3, 1, '{"answer": "1"}', now()),
    (3, 2, '{"answer": "1"}', now()),
    (3, 3, '{"answer": "1"}', now()),
    (3, 4, '{"answer": "1"}', now()),
    (3, 5, '{"answer": "1"}', now()),
    (3, 6, '{"answer": "1"}', now()),
    (3, 7, '{"answer": "1"}', now()),
    (3, 8, '{"answer": "1"}', now()),
    (3, 9, '{"answer1": "1", "answer2" :  "2"}', now());

insert into token (user_id, token, expire_date)
values (1, '557sa7', now()),
       (4, 'fs952j', now()+interval'3d');

