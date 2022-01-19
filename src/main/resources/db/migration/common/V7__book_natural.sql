-- it work's for iTunes ;)
-- purposely bad choice
DROP TABLE IF EXISTS book_natural;

CREATE TABLE book_natural
(
    title     VARCHAR(255) NOT NULL,
    isbn      VARCHAR(255),
    publisher VARCHAR(255),
    PRIMARY KEY (title)
) ENGINE = InnoDB;