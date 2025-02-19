--liquibase formatted sql

--changeset Qooriq:1
CREATE TABLE books(
    id BIGSERIAL PRIMARY KEY,
    isbn VARCHAR(16) NOT NULL UNIQUE,
    name VARCHAR(64) NOT NULL ,
    genre VARCHAR(64) NOT NULL ,
    book_status VARCHAR(16) NOT NULL,
    description VARCHAR(128),
    author VARCHAR(64) NOT NULL
);

