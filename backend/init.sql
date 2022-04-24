CREATE TABLE IF NOT EXISTS test (
    id int,
    name varchar not null,
    PRIMARY KEY (id)
);

INSERT INTO test (id, name) 
VALUES 
(3, 'supertest3'),
(2, 'supertest2'),
(1, 'supertest1');
