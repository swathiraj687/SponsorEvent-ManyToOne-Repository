INSERT INTO event (name, date)
SELECT 'TechCon', '2023-12-15'
WHERE NOT EXISTS (SELECT 1 FROM event WHERE id = 1);

INSERT INTO event (name, date)
SELECT 'Fashion Fest', '2023-11-05'
WHERE NOT EXISTS (SELECT 2 FROM event WHERE id = 2);

INSERT INTO event (name, date)
SELECT 'MusicFest', '2024-01-25'
WHERE NOT EXISTS (SELECT 3 FROM event WHERE id = 3);

INSERT INTO event (name, date)
SELECT 'EcoAwareness Conclave', '2023-11-10'
WHERE NOT EXISTS (SELECT 4 FROM event WHERE id = 4);

INSERT INTO sponsor (name, industry)
SELECT 'TechCorp', 'Technology'
WHERE NOT EXISTS (SELECT 1 FROM sponsor WHERE id = 1);

INSERT INTO sponsor (name, industry)
SELECT 'Glamour Inc.', 'Fashion'
WHERE NOT EXISTS (SELECT 2 FROM sponsor WHERE id = 2);

INSERT INTO sponsor (name, industry)
SELECT 'SoundWave Productions', 'Music Production'
WHERE NOT EXISTS (SELECT 3 FROM sponsor WHERE id = 3);

INSERT INTO sponsor (name, industry)
SELECT 'EcoPlanet', 'Environmental Conservation'
WHERE NOT EXISTS (SELECT 4 FROM sponsor WHERE id = 4);

INSERT INTO event_sponsor (eventId, sponsorId)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM event_sponsor WHERE eventId = 1 AND sponsorId = 1);

INSERT INTO event_sponsor (eventId, sponsorId)
SELECT 1, 2
WHERE NOT EXISTS (SELECT 2 FROM event_sponsor WHERE eventId = 1 AND sponsorId = 2);

INSERT INTO event_sponsor (eventId, sponsorId)
SELECT 2, 2
WHERE NOT EXISTS (SELECT 3 FROM event_sponsor WHERE eventId = 2 AND sponsorId = 2);

INSERT INTO event_sponsor (eventId, sponsorId)
SELECT 3, 3
WHERE NOT EXISTS (SELECT 4 FROM event_sponsor WHERE eventId = 3 AND sponsorId = 3);

INSERT INTO event_sponsor (eventId, sponsorId)
SELECT 3, 4
WHERE NOT EXISTS (SELECT 5 FROM event_sponsor WHERE eventId = 3 AND sponsorId = 4);

INSERT INTO event_sponsor (eventId, sponsorId)
SELECT 4, 4
WHERE NOT EXISTS (SELECT 6 FROM event_sponsor WHERE eventId = 4 AND sponsorId = 4);