CREATE TABLE IF NOT EXISTS Film (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title varchar(255) NOT NULL,
    directors varchar(255) NOT NULL,
    genres varchar(255) NOT NULL,
    age_limit INT NOT NULL,
    duration INT NOT NULL
);

CREATE TABLE IF NOT EXISTS WatchHistory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    film_id INT,
    FOREIGN KEY (film_id) REFERENCES Film(id)
);


CREATE TABLE Session (
    id INT PRIMARY KEY,
    time TIMESTAMP,
    isSeatFree TEXT,
    film_id INT,
    language VARCHAR(255),
    FOREIGN KEY (film_id) REFERENCES Film(id)
);