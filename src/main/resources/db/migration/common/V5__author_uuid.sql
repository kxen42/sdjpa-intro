DROP TABLE IF EXISTS author_uuid;

CREATE TABLE author_uuid
(
    id         VARCHAR(36) NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE = InnoDB;