CREATE TABLE type_ (
  id   INTEGER NOT NULL PRIMARY KEY,
  name VARCHAR
);

CREATE TABLE pokemon (
  id          INTEGER NOT NULL PRIMARY KEY,
  name        VARCHAR,
  description TEXT,
  weight      DOUBLE PRECISION,
  height      INTEGER
);

CREATE TABLE pokemon_type (
  id         INTEGER NOT NULL PRIMARY KEY,
  type_id    INTEGER NOT NULL REFERENCES type_(id),
  pokemon_id INTEGER NOT NULL REFERENCES pokemon(id)
);
