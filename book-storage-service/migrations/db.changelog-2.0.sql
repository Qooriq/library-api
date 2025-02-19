--liquibase formatted sql

--changeset Qooriq:1
INSERT INTO books(isbn, name, genre, book_status, description, author) VALUES
('asdfsadf1', 'Bill', 'MYSTERY', 'AVAILABLE', null, 'Alex'),
('asdfsadf2', 'Cypher', 'MYSTERY', 'AVAILABLE', null, 'Hirsh'),
('asdfsadf3', 'Incognito', 'MYSTERY', 'AVAILABLE', null, 'Ven');
