DROP TABLE IF EXISTS author_jpa;

CREATE TABLE author_jpa
(
    id         BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
) ENGINE = InnoDB;

INSERT INTO author_jpa (first_name, last_name)
VALUES ('Craig', 'Walls');
INSERT INTO author_jpa (first_name, last_name)
VALUES ('J.R.R.', 'Tolkien');


