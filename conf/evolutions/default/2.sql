# --- !Ups

CREATE TABLE rating (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    score FLOAT NOT NULL,
    comment VARCHAR
);

# --- !Downs

DROP TABLE IF EXISTS rating;
