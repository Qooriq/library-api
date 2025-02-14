--liquibase formatted sql

--changeset Qooriq:1
CREATE TABLE users(
    id UUID PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(128) NOT NULL,
    user_status VARCHAR(16) NOT NULL,
    role VARCHAR(8)
);

--changeset Qooriq:2
CREATE TABLE books(
    id BIGSERIAL PRIMARY KEY,
    isbn VARCHAR(16) NOT NULL UNIQUE,
    name VARCHAR(64) NOT NULL ,
    genre VARCHAR(64) NOT NULL ,
    book_status VARCHAR(16) NOT NULL,
    description VARCHAR(128),
    author VARCHAR(64) NOT NULL
);

--changeset Qooriq:3
CREATE TABLE book_tracker(
  id BIGSERIAL PRIMARY KEY,
  book_id BIGINT NOT NULL REFERENCES books(id) ON DELETE CASCADE,
  book_status VARCHAR(16) NOT NULL,
  took_at TIMESTAMP,
  took_by UUID REFERENCES users(id),
  return_before TIMESTAMP CHECK (return_before > took_at)
);
