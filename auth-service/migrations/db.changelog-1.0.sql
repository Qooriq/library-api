--liquibase formatted sql

--changeset Qooriq:1
CREATE TABLE users(
    id UUID PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL
);

