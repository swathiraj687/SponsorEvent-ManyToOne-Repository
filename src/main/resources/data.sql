insert into sponser(id, name, industry) values(1, TechCorp, Technology);
insert into sponser(id, name, industry) values(2, Glamour Inc., Fashion);
insert into sponser(id, name, industry) values(3, SoundWave Productions, Music Production);
insert into sponser(id, name, industry) values(4, EcoPlanet, Environmental Conservation);


insert into event(id, name, date) values(1, TechCorp, 2023-12-15);
insert into event(id, name, date) values(2, Fashion Fest, 2023-11-05);
insert into event(id, name, date) values(3, MusicFest, 2024-01-25);
insert into event(id, name, date) values(3, EcoAwareness Conclave, 2023-11-10);


insert into event_sponser(eventId, sponserId) values(1, 1);
insert into event_sponser(eventId, sponserId) values(1, 2);
insert into event_sponser(eventId, sponserId) values(2, 2);
insert into event_sponser(eventId, sponserId) values(3, 3);
insert into event_sponser(eventId, sponserId) values(3, 4);
insert into event_sponser(eventId, sponserId) values(4, 4);








