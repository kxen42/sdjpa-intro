-- H2 specific
-- use IDENTITY to support nullable PK JPA GenerateType.IDENTITY
-- under the covers it is a bigint so use that for the FKs
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;

CREATE TABLE book
(
    id IDENTITY NOT NULL,
    author_id BIGINT,
    isbn      VARCHAR(255),
    publisher VARCHAR(255),
    title     VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE author
(
    id IDENTITY NOT NULL,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    PRIMARY KEY (id)
);