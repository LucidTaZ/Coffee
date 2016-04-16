# TODO: Evolutions should probably be replaced by this:
# http://slick.typesafe.com/doc/3.1.1/gettingstarted.html#populating-the-database

# --- !Ups

CREATE TABLE flavor (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR
);

# --- !Downs

DROP TABLE IF EXISTS flavor;
