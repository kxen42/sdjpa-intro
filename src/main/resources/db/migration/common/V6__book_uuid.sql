-- Binary UUID
-- expect this field to take half as much space as the VARCHAR UUID
-- work around for 'hibernate and h2 database UUID validation error' use VARBINARY rather than BINARY
DROP TABLE IF EXISTS book_uuid;

CREATE TABLE book_uuid
(
    id        VARBINARY(16) NOT NULL,
    isbn      VARCHAR(255),
    publisher VARCHAR(255),
    title     VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE = InnoDB;
