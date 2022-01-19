DROP TABLE IF EXISTS author;

CREATE TABLE author
(
    id         BIGINT NOT NULL,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    PRIMARY KEY (id)
) ENGINE = InnoDB;