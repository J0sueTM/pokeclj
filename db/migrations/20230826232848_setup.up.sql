CREATE TABLE pokemon_type (
       id   INTEGER NOT NULL,
       name VARCHAR,

       CONSTRAINT pk_pokemon_type PRIMARY KEY (id)
);

CREATE TABLE pokemon (
       id          INTEGER NOT NULL,
       name        VARCHAR,
       description TEXT,
       weigth      DOUBLE PRECISION,
       height      INTEGER,

       CONSTRAINT pk_pokemon PRIMARY KEY (id)
);
