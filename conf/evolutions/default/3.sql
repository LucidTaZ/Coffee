# --- !Ups

CREATE TABLE roasting (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    flavor_id INTEGER NOT NULL,
    duration_seconds INTEGER NOT NULL,
    comment VARCHAR
);

# --- !Downs

DROP TABLE IF EXISTS roasting;
