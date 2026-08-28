-- 1. USERS
INSERT INTO users (id, username, hashed_pass, email, created_at) VALUES
                                                                     (1, 'jdoe', '$2a$10$abcdefghijklmnopqrstuv', 'jdoe@example.com', '2024-01-10T09:15:00'),
                                                                     (2, 'mgarcia', '$2a$10$1234567890abcdefghijk', 'mgarcia@example.com', '2024-02-05T14:30:00'),
                                                                     (3, 'lrossi', '$2a$10$zzzzyyyyxxxxwwwwvvvvuu', 'lrossi@example.com', '2024-03-20T18:45:00'),
                                                                     (4, 'asmith', '$2a$10$qwertyuiopasdfghjklzxc', 'asmith@example.com', '2024-06-15T11:20:00'),
                                                                     (5, 'bchen', '$2a$10$mnbvcxzlkjhgfdsaqwerty', 'bchen@example.com', '2024-07-22T16:05:00'),
                                                                     (6, 'kkumar', '$2a$10$poiuytrewqasdfghjklmnb', 'kkumar@example.com', '2024-08-01T09:40:00');

-- 2. ALBUMS (Imágenes fijadas de CDNs públicos de alta disponibilidad)
INSERT INTO albums (id, name, artist, cover_url) VALUES
                                                     (1, 'A Night at the Opera', 'Queen', 'https://i.scdn.co/image/ab67616d0000b273e319baafd16e84f0408af2a0'),
                                                     (2, 'Hotel California', 'Eagles', 'https://i.scdn.co/image/ab67616d0000b27387c2b5ebf06798e1694eb090'),
                                                     (3, 'Imagine', 'John Lennon', 'https://i.scdn.co/image/ab67616d0000b273b749cd97fb72a15995cf58f5'),
                                                     (4, 'Thriller', 'Michael Jackson', 'https://i.scdn.co/image/ab67616d0000b2734121faee8df82c526abab2fd'),
                                                     (5, 'Nevermind', 'Nirvana', 'https://i.scdn.co/image/ab67616d0000b273e178a3ea56fcb73752e00e00'),
                                                     (6, 'Abbey Road', 'The Beatles', 'https://i.scdn.co/image/ab67616d0000b273dc30583ba717007b00cceb25'),
                                                     (7, 'Random Access Memories', 'Daft Punk', 'https://i.scdn.co/image/ab67616d0000b273b33c09e7f53f9e9cf2ec004f');

-- 3. SONGS
INSERT INTO songs (id, name, duration, lyrics, album_id) VALUES
                                                             (1, 'Bohemian Rhapsody', 355.0, 'Is this the real life? Is this just fantasy? Caught in a landslide, no escape from reality.', 1),
                                                             (2, 'Hotel California', 391.0, 'On a dark desert highway, cool wind in my hair. Warm smell of colitas, rising up through the air.', 2),
                                                             (3, 'Imagine', 183.0, 'Imagine there''s no countries. It isn''t hard to do. Nothing to kill or die for, and no religion, too.', 3),
                                                             (4, 'Billie Jean', 294.0, 'Billie Jean is not my lover. She''s just a girl who claims that I am the one. But the kid is not my son.', 4),
                                                             (5, 'Smells Like Teen Spirit', 301.0, 'Load up on guns, bring your friends. It''s fun to lose and to pretend. She''s over-bored and self-assured.', 5),
                                                             (6, 'Come Together', 259.0, 'Here come old flat top he come grooving up slowly he got joo-joo eyeball he one holy roller.', 6),
                                                             (7, 'Get Lucky', 369.0, 'Like the legend of the phoenix, all ends with beginnings. What keeps the planet spinning?', 7),
                                                             (8, 'Here Comes the Sun', 185.0, 'Here comes the sun doo-doo-doo-doo. Here comes the sun, and I say it''s all right.', 6),
                                                             (9, 'Instant Crush', 337.0, 'I chained alternative to loud. I picture look into the crowd. I met you once, I think I knew.', 7);

-- 4. COVERS (Videos oficiales funcionales de YouTube)
INSERT INTO covers (id, duration, created_at, play_count, uploader_user_id, song_id, youtube_url) VALUES
                                                                                                      (1, 200, '2024-01-15T10:00:00', 1520, 1, 1, NULL),
                                                                                                      (2, 175, '2024-02-10T16:20:00', 890, 2, 2, NULL),
                                                                                                      (3, 230, '2024-03-01T08:05:00', 4300, 1, 3, NULL),
                                                                                                      (4, 190, '2024-04-12T20:40:00', 275, 3, 4, NULL),
                                                                                                      (5, 220, '2024-05-05T12:15:00', 1010, 2, 5, NULL),
                                                                                                      (6, 265, '2024-06-20T14:00:00', 5040, 4, 6, 'https://www.youtube.com/watch?v=1E6VVuNm39M'),
                                                                                                      (7, 300, '2024-07-25T19:30:00', 12300, 5, 7, 'https://www.youtube.com/watch?v=518XP8prwZo'),
                                                                                                      (8, 190, '2024-08-05T21:10:00', 850, 1, 8, 'https://www.youtube.com/watch?v=GKdl-GCsNJ0'),
                                                                                                      (9, 210, '2024-08-12T13:15:00', 340, 6, 9, 'https://www.youtube.com/watch?v=a5uQMwRMHcs'),
                                                                                                      (10, 340, '2024-08-18T10:00:00', 9800, 3, 1, 'https://www.youtube.com/watch?v=fJ9rUzIMcZQ'),
                                                                                                      (11, 180, '2024-08-20T15:45:00', 150, 2, 3, 'https://www.youtube.com/watch?v=YkgkThbgXqy');