insert into users (email, username, password)
values ('test@gmail.com','test', 'test');

INSERT INTO todo (title, description, targetDate, isDone, user_id) VALUES
    ('Complete assignment', 'Finish the math assignment', '2024-07-20', FALSE, (select id from users where username = 'test'));

INSERT INTO todo (title, description, targetDate, isDone, user_id) VALUES
    ('Grocery shopping', 'Buy fruits and vegetables', '2024-07-15', TRUE, (select id from users where username = 'test'));

INSERT INTO todo (title, description, targetDate, isDone, user_id) VALUES
    ('Read book', 'Read "The Catcher in the Rye"', '2024-07-25', FALSE, (select id from users where username = 'test'));


