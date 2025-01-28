-- DELETE ALL TABLES (optional, use only if you want to reset data each time)
DELETE FROM loan;
DELETE FROM book;
DELETE FROM category;
DELETE FROM user_role;
DELETE FROM role;
DELETE FROM user;

-- INSERT INTO role
INSERT INTO role (name) VALUES ('ROLE_USER');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');

-- INSERT INTO user
INSERT INTO user (name, username, password)
VALUES
('Alice', 'alice', '$2a$10$KdHd9kZSPW/y.5GsfpNgOeWSkkMQMJ9iDYfqtmg/J1lvxKGBZfvzO'), -- bcrypt: password123
('Bob', 'bob', '$2a$10$KdHd9kZSPW/y.5GsfpNgOeWSkkMQMJ9iDYfqtmg/J1lvxKGBZfvzO'),
('Charlie', 'charlie', '$2a$10$KdHd9kZSPW/y.5GsfpNgOeWSkkMQMJ9iDYfqtmg/J1lvxKGBZfvzO'),
('Diana', 'diana', '$2a$10$KdHd9kZSPW/y.5GsfpNgOeWSkkMQMJ9iDYfqtmg/J1lvxKGBZfvzO'),
('Eve', 'eve', '$2a$10$KdHd9kZSPW/y.5GsfpNgOeWSkkMQMJ9iDYfqtmg/J1lvxKGBZfvzO');

-- INSERT INTO user_role
INSERT INTO user_role (user_id, role_id)
VALUES
(1, 1), -- Alice -> ROLE_USER
(2, 1), -- Bob -> ROLE_USER
(3, 1), -- Charlie -> ROLE_USER
(4, 2), -- Diana -> ROLE_ADMIN
(5, 2); -- Eve -> ROLE_ADMIN

-- INSERT INTO category
INSERT INTO category (name)
VALUES
('Science Fiction'),
('Fantasy'),
('Mystery'),
('Non-Fiction'),
('Biography');

-- INSERT INTO book
INSERT INTO book (title, author, borrowed, category_id)
VALUES
('Dune', 'Frank Herbert', FALSE, 1),
('The Hobbit', 'J.R.R. Tolkien', FALSE, 2),
('Sherlock Holmes', 'Arthur Conan Doyle', FALSE, 3),
('Sapiens', 'Yuval Noah Harari', FALSE, 4),
('Steve Jobs', 'Walter Isaacson', FALSE, 5);

-- INSERT INTO loan
INSERT INTO loan (book_id, user_id, loan_date, return_date)
VALUES
(1, 1, '2025-01-01', '2025-01-15'),
(2, 2, '2025-01-02', '2025-01-16'),
(3, 3, '2025-01-03', '2025-01-17'),
(4, 4, '2025-01-04', '2025-01-18'),
(5, 5, '2025-01-05', NULL);
