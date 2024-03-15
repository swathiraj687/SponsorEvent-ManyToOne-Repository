CREATE TABLE IF NOT EXISTS event (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    date DATE
);

CREATE TABLE IF NOT EXISTS sponsor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    industry VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS event_sponsor (
    eventId INT,
    sponsorId INT,
    PRIMARY KEY(eventId, sponsorId),
    FOREIGN KEY(eventId) REFERENCES event(id),
    FOREIGN KEY(sponsorId) REFERENCES sponsor(id)
);