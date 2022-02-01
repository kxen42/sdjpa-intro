DROP TABLE IF EXISTS author_jdbc;

CREATE TABLE author_jdbc
(
    id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name  VARCHAR(50)
) ENGINE = InnoDB;

insert into author_jdbc values (1, 'Joseph', 'Campbell');
insert into author_jdbc values (2, 'C.S.', 'Lewis');