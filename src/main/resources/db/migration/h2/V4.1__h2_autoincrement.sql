-- H2 specific
-- use IDENTITY to support nullable PK JPA GenerateType.IDENTITY
-- under the covers it is a bigint so use that for the FKs
drop table if exists book;
drop table if exists author;

create table book
(
    id        identity not null,
    author_id  bigint,
    isbn      varchar(255),
    publisher varchar(255),
    title     varchar(255),
    primary key (id)
);

create table author
(
    id         identity not null,
    first_name varchar(50),
    last_name  varchar(50),
    primary key (id)
);