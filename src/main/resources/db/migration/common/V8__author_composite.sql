-- only stubborn know nothings would use this
DROP TABLE IF EXISTS author_composite;

CREATE TABLE author_composite
(
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    twitter   VARCHAR(255),
    PRIMARY KEY (first_name, last_name)
) ENGINE = InnoDB;