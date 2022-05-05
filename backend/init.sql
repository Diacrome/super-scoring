CREATE TABLE IF NOT EXISTS test (
    id int,
    name varchar not null,
    PRIMARY KEY (id)
);

create table users
(
    user_id  serial primary key,
    login    varchar(64)  not null,
    password varchar(128) not null,
    role     varchar(16)  not null
);

create table tests
(
    test_id       serial primary key,
    creator_id    integer not null references users (user_id),
    name          text    not null,
    description   text,
    date_created  timestamp without time zone default (now() at time zone 'utc'),
    date_modified timestamp without time zone
);

create table questions
(
    question_id      serial primary key,
    test_id          integer  not null references tests (test_id),
    question_weight  smallint not null,
    question_wording text     not null,
    payload          text     not null,
    answer           text     not null,
    question_content text,
    date_created     timestamp without time zone default (now() at time zone 'utc'),
    date_modified    timestamp without time zone,
    time_limit       smallint
);

create table test_pass_records
(
    test_pass_record_id serial primary key,
    test_id             integer not null references tests (test_id),
    user_id             integer not null references users (user_id),
    question_ids        int[]   not null,
    time_started        timestamp without time zone,
    time_finished       timestamp without time zone
);

create table answers
(
    answer_id           serial primary key,
    test_pass_record_id integer  not null references test_pass_records (test_pass_record_id),
    question_id         integer  not null references questions (question_id),
    answer              text     not null,
    time_answer         timestamp without time zone not null,
    time_took           smallint not null -- этот параметр не передается пока
);

INSERT INTO users (user_id, login, password, role)
VALUES
    (1, 'hh', 'password', 'user');

INSERT INTO tests (test_id, creator_id, name, description, date_modified)
VALUES
    (1, 1, 'name', 'description', now());

INSERT INTO questions (question_id, test_id, question_weight, question_wording, payload, answer, question_content)
VALUES
    (1, 1, 1, 'wording', 'payload', 'answer', 'content');

INSERT INTO test_pass_records
    (test_pass_record_id, test_id, user_id, question_ids, time_started, time_finished)
VALUES (1, 1, 1, '{1}', null, null);
