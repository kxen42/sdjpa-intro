-- MySQL supports auto_increment on a nullable column
-- H2 does not support auto_increment on a nullable column
ALTER TABLE book
    CHANGE id id BIGINT AUTO_INCREMENT;
ALTER TABLE author
    CHANGE id id BIGINT AUTO_INCREMENT;