CREATE TABLE IF NOT EXISTS Film (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title varchar(255) NOT NULL,
    directors varchar(255) NOT NULL,
    genres varchar(255) NOT NULL,
    age_limit INT NOT NULL,
    duration INT NOT NULL
);