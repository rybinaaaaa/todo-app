CREATE TABLE IF NOT EXISTS users (
                                    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                    username VARCHAR(50) NOT NULL UNIQUE,
                                    email VARCHAR(100) NOT NULL,
                                    password VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS todo (
                                    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                    title VARCHAR(255) NOT NULL,
                                    description TEXT,
                                    targetDate DATE,
                                    isDone BOOLEAN,
                                    user_id INT,
                                    CONSTRAINT FK_User_Todo FOREIGN KEY (user_id) REFERENCES users(id)
);