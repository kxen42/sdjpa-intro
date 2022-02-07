DROP TABLE IF EXISTS book_jdbctemplate CASCADE;
DROP TABLE IF EXISTS author_jdbctemplate;

CREATE TABLE author_jdbctemplate
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
) ENGINE = InnoDB;

CREATE TABLE book_jdbctemplate
(
    id        BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    isbn      VARCHAR(255),
    publisher VARCHAR(255),
    title     VARCHAR(255),
    author_id BIGINT
) ENGINE = InnoDB;

ALTER TABLE book_jdbctemplate
    ADD CONSTRAINT jdbctemplate_book_author_fk FOREIGN KEY (author_id) REFERENCES author_jdbctemplate (id);

INSERT INTO author_jdbctemplate (first_name, last_name)
VALUES ('Craig', 'Walls');

INSERT INTO book_jdbctemplate (isbn, publisher, title, author_id)
VALUES ('978-1617294945', 'Simon & Schuster',
        'Spring in Action, 5th Edition', (SELECT id FROM author_jdbctemplate WHERE first_name = 'Craig' AND last_name = 'Walls'));

INSERT INTO book_jdbctemplate (isbn, publisher, title, author_id)
VALUES ('978-1617292545', 'Simon & Schuster',
        'Spring Boot in Action, 1st Edition',
        (SELECT id FROM author_jdbctemplate WHERE first_name = 'Craig' AND last_name = 'Walls'));

INSERT INTO book_jdbctemplate (isbn, publisher, title, author_id)
VALUES ('978-1617297571', 'Simon & Schuster',
        'Spring in Action, 6th Edition', (SELECT id FROM author_jdbctemplate WHERE first_name = 'Craig' AND last_name = 'Walls'));

INSERT INTO author_jdbctemplate (first_name, last_name)
VALUES ('Eric', 'Evans');

INSERT INTO book_jdbctemplate (isbn, publisher, title, author_id)
VALUES ('978-0321125217', 'Addison Wesley',
        'Domain-Driven Design', (SELECT id FROM author_jdbctemplate WHERE first_name = 'Eric' AND last_name = 'Evans'));

INSERT INTO author_jdbctemplate (first_name, last_name)
VALUES ('Robert', 'Martin');

INSERT INTO book_jdbctemplate (isbn, publisher, title, author_id)
VALUES ('978-0134494166', 'Addison Wesley',
        'Clean Code', (SELECT id FROM author_jdbctemplate WHERE first_name = 'Robert' AND last_name = 'Martin'));