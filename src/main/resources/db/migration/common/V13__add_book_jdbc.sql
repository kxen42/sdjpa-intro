DROP TABLE IF EXISTS book_jdbc CASCADE;

CREATE TABLE book_jdbc
(
    id             BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    isbn           VARCHAR(255),
    publisher      VARCHAR(255),
    title          VARCHAR(255),
    author_jdbc_id BIGINT
) ENGINE = InnoDB;

ALTER TABLE `bookdb`.`book_jdbc`
    ADD INDEX `id_idx` (`author_jdbc_id` ASC) VISIBLE;
;
ALTER TABLE `bookdb`.`book_jdbc`
    ADD CONSTRAINT `id`
        FOREIGN KEY (`author_jdbc_id`)
            REFERENCES `bookdb`.`author_jdbc` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE;


INSERT INTO author_jdbc (first_name, last_name)
VALUES ('Craig', 'Walls');
INSERT INTO author_jdbc (first_name, last_name)
VALUES ('Eric', 'Evans');

INSERT INTO book_jdbc (isbn, publisher, title, author_jdbc_id)
VALUES ('978-1617294945', 'Simon & Schuster',
        'Spring in Action, 5th Edition',
        (select id from author_jdbc where first_name = 'Craig' and last_name = 'Walls'));

INSERT INTO book_jdbc (isbn, publisher, title, author_jdbc_id)
VALUES ('978-1617292545', 'Simon & Schuster',
        'Spring Boot in Action, 1st Edition',
        (select id from author_jdbc where first_name = 'Craig' and last_name = 'Walls'));

INSERT INTO book_jdbc (isbn, publisher, title, author_jdbc_id)
VALUES ('978-1617297571', 'Simon & Schuster',
        'Spring in Action, 6th Edition',
        (select id from author_jdbc where first_name = 'Craig' and last_name = 'Walls'));



INSERT INTO book_jdbc (isbn, publisher, title, author_jdbc_id)
VALUES ('978-0321125217', 'Addison Wesley',
        'Domain-Driven Design',
        (select id from author_jdbc where first_name = 'Eric' and last_name = 'Evans'));


INSERT INTO book_jdbc (isbn, publisher, title, author_jdbc_id)
VALUES ('978-0134494166', 'Addison Wesley',
        'Clean Code',
        (select id from author_jdbc where first_name = 'Eric' and last_name = 'Evans'));
