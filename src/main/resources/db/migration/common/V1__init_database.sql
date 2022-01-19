DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS hibernate_sequence;

CREATE TABLE book
(
    id        BIGINT not null,
    isbn      VARCHAR(255),
    publisher VARCHAR(255),
    title     VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE hibernate_sequence
(
    next_val BIGINT
) ENGINE = InnoDB;

INSERT INTO hibernate_sequence
VALUES (1);