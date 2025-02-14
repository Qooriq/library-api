--liquibase formatted sql

--changeset Qooriq:1
INSERT INTO users (id, username, password, user_status, role) VALUES
('0380a665-b827-49b1-9630-e716fc21cc73', 'gadd@gmail.com', '{noop}431', 'ACTIVE', 'ADMIN'),
('3f0976bd-d66f-4f29-9f2f-ed7e64f29756', 'as@gmail.com', '{noop}231', 'ACTIVE', 'USER'),
('f58f0a28-726f-43cd-a3b0-8c7d921e30ec', 'tha@gmail.com', '{noop}123', 'ACTIVE', 'USER');

--changeset Qooriq:2
INSERT INTO books(isbn, name, genre, book_status, description, author) VALUES
('asdfsadf1', 'Bill', 'MYSTERY', 'AVAILABLE', null, 'Alex'),
('asdfsadf2', 'Cypher', 'MYSTERY', 'AVAILABLE', null, 'Hirsh'),
('asdfsadf3', 'Incognito', 'MYSTERY', 'AVAILABLE', null, 'Ven');

--changeset Qooriq:3
INSERT INTO book_tracker(book_id, book_status, took_at, took_by, return_before) VALUES
(1, 'AVAILABLE', null, null, null),
(1, 'TAKEN', now(), '0380a665-b827-49b1-9630-e716fc21cc73', now() + INTERVAL '7' DAY),
(2, 'AVAILABLE', null, null, null),
(2, 'AVAILABLE', null, null, null),
(3, 'TAKEN', now(), 'f58f0a28-726f-43cd-a3b0-8c7d921e30ec', now() + INTERVAL '7' DAY),
(3, 'AVAILABLE', null, null, null)