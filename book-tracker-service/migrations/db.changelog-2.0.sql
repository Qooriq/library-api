--liquibase formatted sql

--changeset Qooriq:1
INSERT INTO book_tracker(isbn, book_status, took_at, took_by, return_before) VALUES
('asdfsadf1', 'AVAILABLE', null, null, null),
('asdfsadf1', 'TAKEN', now(), '0380a665-b827-49b1-9630-e716fc21cc73', now() + INTERVAL '7' DAY),
('asdfsadf2', 'AVAILABLE', null, null, null),
('asdfsadf2', 'AVAILABLE', null, null, null),
('asdfsadf3', 'TAKEN', now(), 'f58f0a28-726f-43cd-a3b0-8c7d921e30ec', now() + INTERVAL '7' DAY),
('asdfsadf3', 'AVAILABLE', null, null, null)