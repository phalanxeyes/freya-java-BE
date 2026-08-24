-- USERS
INSERT INTO users (id, username, hashed_pass, email, created_at) VALUES
(1, 'jdoe', '$2a$10$abcdefghijklmnopqrstuv', 'jdoe@example.com', '2024-01-10T09:15:00'),
(2, 'mgarcia', '$2a$10$1234567890abcdefghijk', 'mgarcia@example.com', '2024-02-05T14:30:00'),
(3, 'lrossi', '$2a$10$zzzzyyyyxxxxwwwwvvvvuu', 'lrossi@example.com', '2024-03-20T18:45:00');

-- SONGS (títulos y duraciones reales)
INSERT INTO songs (id, name, duration, lyrics) VALUES
(1, 'Bohemian Rhapsody', 355.0, 'Is this the real life? Is this just fantasy? Caught in a landslide, no escape from reality.'),
(2, 'Hotel California', 391.0, 'On a dark desert highway, cool wind in my hair. Warm smell of colitas, rising up through the air.'),
(3, 'Imagine', 183.0, 'Imagine there''s no countries. It isn''t hard to do. Nothing to kill or die for, and no religion, too.'),
(4, 'Billie Jean', 294.0, 'Billie Jean is not my lover. She''s just a girl who claims that I am the one. But the kid is not my son.'),
(5, 'Smells Like Teen Spirit', 301.0, 'Load up on guns, bring your friends. It''s fun to lose and to pretend. She''s over-bored and self-assured.');

-- COVERS (con FKs a users y songs)
INSERT INTO covers (id, duration, created_at, play_count, created_by_user_id, song_id) VALUES
(1, 200, '2024-01-15T10:00:00', 1520, 1, 1),
(2, 175, '2024-02-10T16:20:00', 890, 2, 2),
(3, 230, '2024-03-01T08:05:00', 4300, 1, 3),
(4, 190, '2024-04-12T20:40:00', 275, 3, 4),
(5, 220, '2024-05-05T12:15:00', 1010, 2, 5);