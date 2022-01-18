-- MySQL supports auto_increment on a nullable column
-- H2 does not support auto_increment on a nullable column
alter table book change id id bigint auto_increment;
alter table author change id id bigint auto_increment;