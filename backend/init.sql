create table ss_user
(
    id       serial primary key,
    login    varchar(64)  not null,
    password varchar(128) not null,
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
    date_modified     timestamp without time zone
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
    time_finished timestamp without time zone
);

create table test_pass_question_id
(
    id                serial primary key,
    test_pass_id      integer  not null references test_pass (id),
    question_id_order smallint not null,
    question_id       integer  not null references question (id)
);

create table answer
(
    test_pass_question_id integer                     not null references test_pass_question_id (id),
    answer                json                        not null,
    time_start            timestamp with time zone    not null,
    time_end              timestamp without time zone not null
);

insert into ss_user (id, login, password, role)
values (1, 'admin', 'admin1', 'admin'),
       (2, 'adminLiza', 'admin2', 'admin'),
       (3, 'user1', 'user1', 'user'),
       (4, 'user2', 'user2', 'user');


insert into test (id, creator_id, name, description, date_created, date_modified)
values (1, 1, 'Математический тест', 'Тест на знание таблицы умножения', now(), now()),
       (2, 2, 'Тест по английскому языку', 'Тест на знание английского языка', now(), now());

insert into question(id, test_id, question_wording, payload, answer, question_content, date_created, date_modified,
                     time_limit)
values (1, 1, '1*1 = ', '{"1": 1, "2": 3, "3": 4, "4": 1}', '{"answer": "1"}', null, now(), now(), 30),
       (2, 1, '2*2 = ', '{"1": 1, "2": 4, "3": 4, "4": 1}', '{"answer": "2"}', null, now(), now(), 30),
       (3, 1, '3*3 = ', '{"1": 1, "2": 3, "3": 9, "4": 1}', '{"answer": "3"}', null, now(), now(), 30),
       (4, 1, '4*4 = ', '{"1": 1, "2": 3, "3": 4, "4": 16}', '{"answer": "4"}', null, now(), now(), 30),
       (5, 1, '5*5 = ', '{"1": 25, "2": 3, "3": 4, "4": 1}', '{"answer": "1"}', null, now(), now(), 30),
       (6, 1, '6*6 = ', '{"1": 1, "2": 36, "3": 4, "4": 1}', '{"answer": "2"}', null, now(), now(), 30),
       (7, 1, '7*7 = ', '{"1": 1, "2": 3, "3": 49, "4": 1}', '{"answer": "3"}', null, now(), now(), 30),
       (8, 1, '8*8 = ', '{"1": 1, "2": 3, "3": 4, "4": 64}', '{"answer": "4"}', null, now(), now(), 30),
       (9, 1, '9*9 = ', '{"1": 81, "2": 3, "3": 4, "4": 1}', '{"answer": "1"}', null, now(), now(), 30),
       (10, 1, '10*10 = ', '{"1": 100, "2": 1000, "3": 4, "4": 1}', '{"answer": "1"}', null, now(), now(), 30),
       (11, 1, '6*5 = ', '{"1": 1, "2": 30, "3": 35, "4": 1}', '{"answer": "2"}', null, now(), now(), 30),
       (12, 1, '7*6 = ', '{"1": 1, "2": 3, "3": 42, "4": 44}', '{"answer": "3"}', null, now(), now(), 30),
       (13, 1, '7*8 = ', '{"1": 1, "2": 3, "3": 4, "4": 56}', '{"answer": "4"}', null, now(), now(), 30),
       (14, 1, '8*9 = ', '{"1": 72, "2": 3, "3": 4, "4": 1}', '{"answer": "1"}', null, now(), now(), 30),
       (15, 1, '6*4 = ', '{"1": 1, "2": 24, "3": 4, "4": 1}', '{"answer": "2"}', null, now(), now(), 30),
       (16, 1, '2*3 = ', '{"1": 1, "2": 3, "3": 6, "4": 1}', '{"answer": "3"}', null, now(), now(), 30),
       (17, 1, '5*4 = ', '{"1": 1, "2": 3, "3": 4, "4": 20}', '{"answer": "4"}', null, now(), now(), 30),
       (18, 2, 'Kang thought the spicy tofu dish was %answer tasty, so he ordered it again.', '{"1": "mainly", "2": "fairy", "3": "especially", "4": "slightly"}', '{"answer": "3"}', null, now(), now(), 30),
       (19, 2, 'They’ve been married for over fifty years, but she still remembers the day she first %answer.', '{"1": "keen on him", "2": "stuck on him", "3": "fell for him", "4": "wed him"}', '{"answer": "3"}', null, now(), now(), 30),
       (20, 2, 'She arrived at 8 p.m., opened the door and shouted “Good %answer!”', '{"1": "morning", "2": "evening", "3": "night", "4": "bye"}', '{"answer": "2"}', null, now(), now(), 30),
       (21, 2, 'It %answer that our Earth is so safe and peaceful planet, doesn''t it?', '{"1": "seems", "2": "is seeming", "3": "seemed"}', '{"answer": "1"}', null, now(), now(), 30),
       (22, 2, 'If I had had a lot of money yesterday, I would a car now.', '{"1": "have bought", "2": "buy", "3": "bought"}', '{"answer": "1"}', null, now(), now(), 30),
       (23, 2, 'For the questions below, please choose the best option to complete the sentence or conversation. \n Can I park here?', '{"1": "Sorry, I did that.", "2": "It''s the same place.", "3": "Only for half an hour."}', '{"answer": "3"}', null, now(), now(), 30),
       (24, 2, ' I''m sorry - I didn''t %answer to disturb you.', '{"1": "hope", "2": "think", "3": "mean", "4": "suppose"}', '{"answer": "3"}', null, now(), now(), 30),
       (25, 2, '%answer tired Melissa is when she gets home from work, she always makes time to say goodnight to the children.', '{"1": "Whatever", "2": "No matter how", "3": "However much", "4": "Although"}', '{"answer": "2"}', null, now(), now(), 30),
       (26, 2, 'How old is your %answer?” – “She''s thirteen.”', '{"1": "brother", "2": "son", "3": "boyfriend", "4": "sister"}', '{"answer": "4"}', null, now(), now(), 30),
       (27, 2, 'Do you like horror movies?', '{"1": "Yes, I am", "2": "Yes, I like", "3": "Yes, I do", "4": "Yes, it is"}', '{"answer": "3"}', null, now(), now(), 30),
       (28, 2, 'Jane Smith – cacti gardener. Many people are fond %answer1 gardening but Jane is different from them – she %answer2 only cacti. As a young girl she liked watching how her mother took care of plants, trees, and bushes in their garden and Jane helped her a lot. Jane’s collection of cacti from all over the world reminds her of her mother and childhood.', '{"answer1":{"1": "plants", "2": "is planting", "3": "has planted"}, "answer2": {"1": "plants", "2": "is planting", "3": "has planted"}}', '{"answer1": "2", "answer2" : "3"}', null, now(), now(), 30);


insert into test_pass (id, test_id, user_id, time_started, time_finished)
values (1, 1, 3, now() - interval '100 seconds', now()),
       (2, 1, 4, now() - interval '200 seconds', null),
       (3, 2, 4, now() - interval '200 seconds', now());


insert into test_pass_question_id (id, test_pass_id, question_id_order, question_id)
values (1, 1, 1, 2),
       (2, 1, 2, 12),
       (3, 1, 3, 13),
       (4, 1, 4, 14),
       (5, 1, 5, 15),
       (6, 1, 6, 7),
       (7, 1, 7, 6),
       (8, 1, 8, 5),
       (9, 1, 9, 4),
       (10, 1, 10, 3),
       (11, 2, 1, 1),
       (12, 2, 2, 3),
       (13, 2, 3, 4),
       (14, 2, 4, 6),
       (15, 2, 5, 7),
       (16, 2, 6, 10),
       (17, 2, 7, 11),
       (18, 2, 8, 13),
       (19, 2, 9, 15),
       (20, 2, 10, 17),
       (21, 3, 1, 18),
       (22, 3, 2, 19),
       (23, 3, 3, 20),
       (24, 3, 4, 21),
       (25, 3, 5, 23),
       (26, 3, 6, 24),
       (27, 3, 7, 25),
       (28, 3, 8, 26),
       (29, 3, 9, 27),
       (30, 3, 10, 28);


insert into answer (test_pass_question_id,  answer, time_start, time_end)
values
    (1, '{"answer": "1"}', now()- interval'200s', now()),
    (2, '{"answer": "1"}', now()-interval'200s', now()),
    (3, '{"answer": "1"}', now()-interval'200s', now()),
    (4, '{"answer": "1"}', now()-interval'200s', now()),
    (5, '{"answer": "1"}', now()-interval'200s', now()),
    (6, '{"answer": "1"}', now()-interval'200s', now()),
    (7, '{"answer": "1"}', now()-interval'200s', now()),
    (8, '{"answer": "1"}', now()-interval'200s', now()),
    (9, '{"answer": "1"}', now()-interval'200s', now()),
    (10, '{"answer": "1"}', now()-interval'200s', now()),
    (11, '{"answer": "1"}', now()-interval'200s', now()),
    (12, '{"answer": "1"}', now()-interval'200s', now()),
    (13, '{"answer": "1"}', now()-interval'200s', now()),
    (14, '{"answer": "1"}', now()-interval'200s', now()),
    (15, '{"answer": "1"}', now()-interval'200s', now()),
    (16, '{"answer": "1"}', now()-interval'200s', now()),
    (17, '{"answer": "1"}', now()-interval'200s', now()),
    (18, '{"answer": "1"}', now()-interval'200s', now()),
    (19, '{"answer": "1"}', now()-interval'200s', now()),
    (21, '{"answer": "1"}', now()-interval'200s', now()),
    (22, '{"answer": "1"}', now()-interval'200s', now()),
    (23, '{"answer": "1"}', now()-interval'200s', now()),
    (24, '{"answer": "1"}', now()-interval'200s', now()),
    (25, '{"answer": "1"}', now()-interval'200s', now()),
    (26, '{"answer": "1"}', now()-interval'200s', now()),
    (27, '{"answer": "1"}', now()-interval'200s', now()),
    (28, '{"answer": "1"}', now()-interval'200s', now()),
    (29, '{"answer": "1"}', now()-interval'200s', now()),
    (30, '{"answer1": "1", "answer2" :  "2"}', now()-interval'200s', now());
