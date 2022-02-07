DROP TABLE IF EXISTS book_jpa CASCADE;

CREATE TABLE book_jpa
(
    id        BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    isbn      VARCHAR(255),
    publisher VARCHAR(255),
    title     VARCHAR(255)
) ENGINE = InnoDB;


INSERT INTO book_jpa (isbn, publisher, title)
VALUES ('978-1617294945', 'Simon & Schuster',
        'Spring in Action, 5th Edition');

INSERT INTO book_jpa (isbn, publisher, title)
VALUES ('978-1617292545', 'Simon & Schuster',
        'Spring Boot in Action, 1st Edition');

INSERT INTO book_jpa (isbn, publisher, title)
VALUES ('978-1617297571', 'Simon & Schuster',
        'Spring in Action, 6th Edition');


INSERT INTO book_jpa (isbn, publisher, title)
VALUES ('978-0321125217', 'Addison Wesley',
        'Domain-Driven Design');


INSERT INTO book_jpa (isbn, publisher, title)
VALUES ('978-0134494166', 'Addison Wesley',
        'Clean Code');
