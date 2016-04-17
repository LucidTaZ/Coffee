# --- !Ups

CREATE TABLE rating (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    roasting_id INT NOT NULL,
    score FLOAT NOT NULL,
    comment VARCHAR
);

# --- !Downs

DROP TABLE IF EXISTS rating;
