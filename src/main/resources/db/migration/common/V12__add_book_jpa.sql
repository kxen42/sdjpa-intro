DROP TABLE IF EXISTS book_jpa CASCADE;

CREATE TABLE book_jpa
(
    id        BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    isbn      VARCHAR(255),
    publisher VARCHAR(255),
    title     VARCHAR(255),
    author_id BIGINT
) ENGINE = InnoDB;


ALTER TABLE book_jpa
    ADD CONSTRAINT book_jpa_author_jpa_fk FOREIGN KEY (author_id) REFERENCES author_jpa (id);


INSERT INTO book_jpa (isbn, publisher, title, author_id)
VALUES ('978-1617294945', 'Simon & Schuster',
        'Spring in Action, 5th Edition',
        (SELECT id FROM author_jpa WHERE first_name = 'Craig' AND last_name = 'Walls'));

INSERT INTO book_jpa (isbn, publisher, title, author_id)
VALUES ('978-1617292545', 'Simon & Schuster',
        'Spring Boot in Action, 1st Edition',
        (SELECT id FROM author_jpa WHERE first_name = 'Craig' AND last_name = 'Walls'));

INSERT INTO book_jpa (isbn, publisher, title, author_id)
VALUES ('978-1617297571', 'Simon & Schuster',
        'Spring in Action, 6th Edition',
        (SELECT id FROM author_jpa WHERE first_name = 'Craig' AND last_name = 'Walls'));

INSERT INTO author_jpa (first_name, last_name)
VALUES ('Eric', 'Evans');

INSERT INTO book_jpa (isbn, publisher, title, author_id)
VALUES ('978-0321125217', 'Addison Wesley',
        'Domain-Driven Design', (SELECT id FROM author_jpa WHERE first_name = 'Eric' AND last_name = 'Evans'));

INSERT INTO author_jpa (first_name, last_name)
VALUES ('Robert', 'Martin');

INSERT INTO book_jpa (isbn, publisher, title, author_id)
VALUES ('978-0134494166', 'Addison Wesley',
        'Clean Code', (SELECT id FROM author_jpa WHERE first_name = 'Robert' AND last_name = 'Martin'));
