CREATE TABLE test(
  id SERIAL PRIMARY KEY,
  name VARCHAR(256) NOT NULL UNIQUE,
  description TEXT NOT NULL UNIQUE,
  date_created TIMESTAMP NOT NULL,
  date_modified TIMESTAMP,
  creator_id INTEGER NOT NULL,
  modifier_id INTEGER
);

INSERT INTO test (name,description,date_created,creator_id) VALUES
  ('Математический тест','Решите 10 математичесчких задач.',NOW(),1),
  ('Ещё какой-то тест','Решите ещё чего-нибудь',NOW(),1);
