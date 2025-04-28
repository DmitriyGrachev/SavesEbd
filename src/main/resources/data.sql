-- Заполнение таблицы users
INSERT INTO users (username, password, email, role) VALUES ('john_doe', '12345', 'john@example.com', 'USER');
INSERT INTO users (username, password, email, role) VALUES ('jane_smith', 'qwerty', 'jane@example.com', 'USER');
INSERT INTO users (username, password, email, role) VALUES ('admin_user', 'admin123', 'admin@example.com', 'ADMIN');

-- Заполнение таблицы genres
INSERT INTO genres (name) VALUES ('Action');
INSERT INTO genres (name) VALUES ('Drama');
INSERT INTO genres (name) VALUES ('Sci-Fi');
INSERT INTO genres (name) VALUES ('Thriller');

-- Заполнение таблицы films (без dtype)
INSERT INTO films (title, release_year, description, director, rating) VALUES ('Inception', 2010, 'A thief who steals corporate secrets through dream infiltration.', 'Christopher Nolan', 8.8);
INSERT INTO films (title, release_year, description, director, rating) VALUES ('The Matrix', 1999, 'A hacker discovers the reality is a simulation.', 'Wachowski Sisters', 8.7);
INSERT INTO films (title, release_year, description, director, rating) VALUES ('Breaking Bad', 2008, 'A chemistry teacher turns to drug production.', 'Vince Gilligan', 9.5);
INSERT INTO films (title, release_year, description, director, rating) VALUES ('Stranger Things', 2016, 'Kids uncover supernatural mysteries.', 'Duffer Brothers', 8.7);

-- Заполнение таблицы movie (дочерняя таблица для фильмов)
INSERT INTO movie (film_id, duration, source ,poster) VALUES (1, 148,'/videos/video.mp4','/images/inception.jpg'); -- Inception
INSERT INTO movie (film_id, duration, source ,poster) VALUES (2, 136, '/videos/video.mp4','/images/inception.jpg'); -- The Matrix

-- Заполнение таблицы serial (дочерняя таблица для сериалов)
INSERT INTO serial (film_id, seasons, episodes) VALUES (3, 5, 62); -- Breaking Bad
INSERT INTO serial (film_id, seasons, episodes) VALUES (4, 4, 34); -- Stranger Things

-- Заполнение таблицы film_genres (связь фильмов/сериалов с жанрами)
INSERT INTO film_genres (film_id, genre_id) VALUES (1, 3); -- Inception -> Sci-Fi
INSERT INTO film_genres (film_id, genre_id) VALUES (1, 4); -- Inception -> Thriller
INSERT INTO film_genres (film_id, genre_id) VALUES (2, 1); -- The Matrix -> Action
INSERT INTO film_genres (film_id, genre_id) VALUES (2, 3); -- The Matrix -> Sci-Fi
INSERT INTO film_genres (film_id, genre_id) VALUES (3, 2); -- Breaking Bad -> Drama
INSERT INTO film_genres (film_id, genre_id) VALUES (3, 4); -- Breaking Bad -> Thriller
INSERT INTO film_genres (film_id, genre_id) VALUES (4, 3); -- Stranger Things -> Sci-Fi
INSERT INTO film_genres (film_id, genre_id) VALUES (4, 2); -- Stranger Things -> Drama

-- Заполнение таблицы comments
INSERT INTO comments (user_id, film_id, text) VALUES (1, 1, 'Amazing movie, loved the dream concept!');
INSERT INTO comments (user_id, film_id, text) VALUES (2, 2, 'Classic action flick, still holds up.');
INSERT INTO comments (user_id, film_id, text) VALUES (1, 3, 'Best series ever, Walter White is iconic.');
INSERT INTO comments (user_id, film_id, text) VALUES (3, 4, 'Great show, the 80s vibe is spot on.');
INSERT INTO comments (user_id, film_id, text) VALUES (2, 1, 'Mind-blowing plot twists!');

UPDATE comments SET time = CURRENT_TIMESTAMP WHERE time IS NULL;

-- 1. Insert the directory
INSERT INTO video_directory (id,description)  --  Added id
VALUES (1,'Motivational'); --  Assuming an ID of 1, you can change this

-- 2. Insert the first video frame (video) and associate it with the directory iVnkwVv9dnk

INSERT INTO video_frame (video_directory_id, title, description, url)
VALUES (1, 'Video 1 Title', 'Description of Video 1', 'https://www.youtube.com/embed/yBrRpb8aLwk');

-- 3. Insert the second video frame (video) and associate it with the same directory
INSERT INTO video_frame (video_directory_id, title, description, url)
VALUES (1, 'Video 2 Title', 'Description of Video 2', 'https://www.youtube.com/embed/iow5V3Qlvwo');