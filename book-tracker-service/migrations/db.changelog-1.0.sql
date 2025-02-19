--liquibase formatted sql

--changeset Qooriq:q
CREATE TABLE book_tracker(
  id BIGSERIAL PRIMARY KEY,
  isbn VARCHAR(64) NOT NULL,
  book_status VARCHAR(16) NOT NULL,
  took_at TIMESTAMP,
  took_by VARCHAR(64),
  return_before TIMESTAMP CHECK (return_before > took_at)
);
