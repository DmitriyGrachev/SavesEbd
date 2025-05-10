
-- 1. Insert Users (MUST be done first to satisfy foreign key constraints)
INSERT INTO users (username, email, password, first_name, last_name, registration_date) VALUES
                                                                                                ( 'user1', 'user1@example.com', 'password123', 'John', 'Doe', CURRENT_TIMESTAMP),
                                                                                                ( 'user2', 'user2@example.com', 'password123', 'Jane', 'Smith', CURRENT_TIMESTAMP),
                                                                                                ( 'user3', 'user3@example.com', 'password123', 'Michael', 'Johnson', CURRENT_TIMESTAMP),
                                                                                                ( 'user4', 'user4@example.com', 'password123', 'Emily', 'Williams', CURRENT_TIMESTAMP),
                                                                                                ( 'user5', 'user5@example.com', 'password123', 'David', 'Brown', CURRENT_TIMESTAMP);

-- 2. Insert Genres
INSERT INTO genres (name) VALUES
                              ('Action'),
                              ('Drama'),
                              ('Sci-Fi'),
                              ('Thriller'),
                              ('Comedy'),
                              ('Romance'),
                              ('Fantasy'),
                              ('Crime');

-- 3. Insert Films
INSERT INTO films (title, release_year, description, director, rating, popularity) VALUES
-- Movies
('Inception',               2010, 'A thief who steals corporate secrets through dream infiltration.',     'Christopher Nolan',            8.8, 1580000),
('The Matrix',              1999, 'A hacker discovers the reality is a simulation.',                     'Wachowski Sisters',            8.7, 1750000),
('Interstellar',            2014, 'A team travels through a wormhole to ensure humanitys survival.',    'Christopher Nolan',            8.6, 1700000),
('The Dark Knight',         2008, 'Batman faces the Joker in Gotham City.',                              'Christopher Nolan',            9.0, 2300000),
('Fight Club',              1999, 'An insomniac meets a soap salesman and forms an underground club.',  'David Fincher',                8.8, 1600000),
('Blade Runner 2049',       2017, 'A new blade runner unearths a long-buried secret.',                  'Denis Villeneuve',             8.0, 1200000),
('Dune',                    2021, 'A noble family fights for control of the desert planet Arrakis.',     'Denis Villeneuve',             8.1, 1500000),
('The Prestige',            2006, 'Two stage magicians engage in a battle to create the ultimate illusion.', 'Christopher Nolan',         8.5, 1100000),
('Mad Max: Fury Road',      2015, 'In a post-apocalyptic wasteland, Max teams up with Furiosa.',        'George Miller',                8.1, 1300000),
('Parasite',                2019, 'Greed and class discrimination threaten a newly formed symbiosis.', 'Bong Joon-ho',                 8.6, 1900000),
('Avengers: Endgame',       2019, 'The Avengers assemble once more to undo Thanoss actions.',          'Anthony & Joe Russo',          8.4, 2400000),
('John Wick',               2014, 'An ex-hitman comes out of retirement to track down the gangsters.',  'Chad Stahelski',               7.4, 1650000),
('The Shawshank Redemption',1994, 'Two imprisoned men bond over years, finding solace and redemption.', 'Frank Darabont',               9.3, 2100000),
('The Godfather',           1972, 'The aging patriarch of a crime dynasty transfers control to his son.', 'Francis Ford Coppola',      9.2, 2300000),
('Pulp Fiction',            1994, 'The lives of two mob hitmen, a boxer, a gangster and his wife intertwine.', 'Quentin Tarantino',        8.9, 1950000),
-- Serials
('Breaking Bad',            2008, 'A chemistry teacher turns to drug production.',                       'Vince Gilligan',               9.5, 2250000),
('Stranger Things',         2016, 'Kids uncover supernatural mysteries in their small town.',            'Duffer Brothers',              8.7, 2100000),
('The Office',              2005, 'A mockumentary about office workers at Dunder Mifflin.',              'Greg Daniels',                 9.0, 2200000),
('Friends',                 1994, 'Six friends navigate life and love in New York City.',                'David Crane & Marta Kauffman', 8.9, 2500000),
('House of the Dragon',     2022, 'The Targaryen civil war set 200 years before Game of Thrones.',      'Ryan Condal',                  8.5, 1750000),
('The Last of Us',          2023, 'A smuggler escorts a girl across post-apocalyptic America.',          'Craig Mazin',                  9.1, 1850000),
('Sherlock',                2010, 'A modern adaptation of Sherlock Holmes.',                            'Mark Gatiss & Steven Moffat',  9.1, 2000000),
('True Detective',          2014, 'Anthology crime drama with dark and twisted stories.',               'Nic Pizzolatto',               9.0, 1700000),
('Narcos',                  2015, 'Chronicles the rise of drug kingpin Pablo Escobar.',                  'Chris Brancato',               8.8, 1500000),
('Chernobyl',               2019, 'A dramatization of the 1986 nuclear disaster.',                      'Craig Mazin',                  9.4, 1650000);

-- 4. Insert Movie Records
INSERT INTO movie (film_id, duration, source, poster) VALUES
                                                              (1,  148, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (2,  136, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (3,  169, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (4,  152, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (5,  139, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (6,  163, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (7,  155, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (8,  130, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (9,  120, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (10, 132, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (11, 181, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (12, 101, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (13, 142, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (14, 175, '/videos/video.mp4', '/images/inception.jpg'),
                                                              (15, 154, '/videos/video.mp4', '/images/inception.jpg');

-- 5. Insert Serial Records
INSERT INTO serial (film_id, seasons, episodes, poster) VALUES
                                                                (16, 5,  62, '/images/inception.jpg'),
                                                                (17, 4,  34, '/images/inception.jpg'),
                                                                (18, 9, 201, '/images/inception.jpg'),
                                                                (19,10,236, '/images/inception.jpg'),
                                                                (20, 1, 10, '/images/inception.jpg'),
                                                                (21, 1,  9, '/images/inception.jpg'),
                                                                (22, 4, 13, '/images/inception.jpg'),
                                                                (23, 4, 24, '/images/inception.jpg'),
                                                                (24, 3, 30, '/images/inception.jpg'),
                                                                (25, 1,  5, '/images/inception.jpg');

-- 6. Connect Films with Genres
INSERT INTO film_genres (film_id, genre_id) VALUES
-- Movies
(1, 3),(1, 4),
(2, 1),(2, 3),
(3, 3),(3, 2),
(4, 1),(4, 4),
(5, 4),(5, 2),
(6, 3),(6, 4),
(7, 3),(7, 7),
(8, 4),(8, 2),
(9, 1),(9, 6),
(10,3),(10,2),
(11,1),(11,4),
(12,1),(12,4),
(13,2),(13,4),
(14,2),(14,4),
(15,1),(15,4),
-- Serials
(16,2),(16,4),
(17,3),(17,2),
(18,5),(18,2),
(19,5),(19,2),
(20,4),(20,8),
(21,2),(21,8),
(22,8),(22,4),
(23,2),(23,6),
(24,1),(24,4),
(25,2),(25,4);

-- 7. Insert Comments
INSERT INTO comments (user_id, film_id, text, time) VALUES
                                                        (1,  1, 'Absolutely mind-bending! Nolan at his best.', CURRENT_TIMESTAMP),
                                                        (2,  3, 'Interstellar blew my mind with its visuals.', CURRENT_TIMESTAMP),
                                                        (3,  5, 'Fight Club is a masterpiece of modern cinema.', CURRENT_TIMESTAMP),
                                                        (4,  7, 'Dune''s world-building is incredible.', CURRENT_TIMESTAMP),
                                                        (5, 11, 'Endgame was an epic conclusion to the saga.', CURRENT_TIMESTAMP),
                                                        (2, 16, 'Breaking Bad is the best series ever made.', CURRENT_TIMESTAMP),
                                                        (3, 17, 'Stranger Things has perfect 80s nostalgia.', CURRENT_TIMESTAMP),
                                                        (1, 18, 'The Office never gets old—hilarious every time.', CURRENT_TIMESTAMP),
                                                        (4, 19, 'Friends defined my childhood.', CURRENT_TIMESTAMP),
                                                        (5, 20, 'House of the Dragon is visually stunning.', CURRENT_TIMESTAMP),
                                                        (2, 21, 'The Last of Us captures the game''s emotion so well.', CURRENT_TIMESTAMP),
                                                        (3, 22, 'Sherlock''s writing and acting are top-notch.', CURRENT_TIMESTAMP),
                                                        (1, 23, 'True Detective''s first season was groundbreaking.', CURRENT_TIMESTAMP),
                                                        (4, 24, 'Narcos is both thrilling and informative.', CURRENT_TIMESTAMP),
                                                        (5, 25, 'Chernobyl gave me chills—so well done.', CURRENT_TIMESTAMP);
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