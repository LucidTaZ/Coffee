# --- !Ups

CREATE TABLE flavor (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS flavor;
