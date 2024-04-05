package com.example.movietime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FilmRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public FilmRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // I asked GPT for an example request to the database and made these find methods based on that
    // Since GPT gave me an example using a JdbcTemplate instead of the newer JdbcClient, I used that instead
    public List<Film> findAll() {
        String sql = "SELECT * FROM Film";
        return jdbcTemplate.query(sql, new RowMapper<Film>() {
            @Override
            public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                String director = rs.getString("directors");
                List<Genre> genres = Arrays.stream(rs.getString("genres").split(";"))
                        .map(Genre::valueOf)
                        .collect(Collectors.toList());
                Integer ageLimit = rs.getInt("age_limit");
                Integer duration = rs.getInt("duration");

                return new Film(id, title, director, genres, ageLimit, duration);
            }
        });
    }

    public Film findById(Integer id) {
        String sql = "SELECT * FROM Film WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<Film>() {
            @Override
            public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
                String title = rs.getString("title");
                String director = rs.getString("directors");
                List<Genre> genres = Arrays.stream(rs.getString("genres").split(";"))
                        .map(Genre::valueOf)
                        .collect(Collectors.toList());
                Integer ageLimit = rs.getInt("age_limit");
                Integer duration = rs.getInt("duration");

                return new Film(id, title, director, genres, ageLimit, duration);
            }
        });
    }

    public List<Film> findByGenre(Genre genre) {
        String sql = "SELECT * FROM Film WHERE genres LIKE ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + genre.name() + "%"}, new RowMapper<Film>() {
            @Override
            public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                String director = rs.getString("directors");
                List<Genre> genres = Arrays.stream(rs.getString("genres").split(";"))
                        .map(Genre::valueOf)
                        .collect(Collectors.toList());
                Integer ageLimit = rs.getInt("age_limit");
                Integer duration = rs.getInt("duration");

                return new Film(id, title, director, genres, ageLimit, duration);
            }
        });
    }

    public List<Film> findByAgeLimit(Integer age) {
        String sql = "SELECT * FROM Film WHERE age_limit <= ?";
        return jdbcTemplate.query(sql, new Object[]{age}, new RowMapper<Film>() {
            @Override
            public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                String director = rs.getString("directors");
                List<Genre> genres = Arrays.stream(rs.getString("genres").split(";"))
                        .map(Genre::valueOf)
                        .collect(Collectors.toList());
                Integer ageLimit = rs.getInt("age_limit");
                Integer duration = rs.getInt("duration");

                return new Film(id, title, director, genres, ageLimit, duration);
            }
        });
    }


    public List<Film> findByGenreAndAgeLimit(Genre genre, Integer ageLimit) {
        String sql = "SELECT * FROM Film WHERE genres LIKE ? AND age_limit <= ?";
        return jdbcTemplate.query(sql, new Object[]{"%" + genre.name() + "%", ageLimit}, new RowMapper<Film>() {
            @Override
            public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                String director = rs.getString("directors");
                List<Genre> genres = Arrays.stream(rs.getString("genres").split(";"))
                        .map(Genre::valueOf)
                        .collect(Collectors.toList());
                Integer age = rs.getInt("age_limit");
                Integer duration = rs.getInt("duration");

                return new Film(id, title, director, genres, age, duration);
            }
        });
    }

    void addFilmToWatchHistory(Film film) {
        String sql = "INSERT INTO watchhistory (film_id) VALUES (?)";
        jdbcTemplate.update(sql, film.id());
    }

    // Method for finding the best film to recommend based on genres of the films and genres of the films in the watch history
    public Film recommendFilm() {
        // Fetch the genres of the films in the WatchHistory table
        String fetchGenresSql = "SELECT f.genres FROM Film f JOIN WatchHistory wh ON f.id = wh.film_id";
        List<String> watchedGenres = jdbcTemplate.queryForList(fetchGenresSql, String.class);

        // Convert the list of genre strings to a list of individual genres
        List<Genre> genres = watchedGenres.stream()
                .flatMap(genresStr -> Arrays.stream(genresStr.split(";")))
                .map(Genre::valueOf)
                .distinct()
                .collect(Collectors.toList());

        // Fetch all films that are not in the WatchHistory table
        String fetchFilmsSql = "SELECT * FROM Film f WHERE f.id NOT IN (SELECT film_id FROM WatchHistory)";
        List<Film> films = jdbcTemplate.query(fetchFilmsSql, new RowMapper<Film>() {
            @Override
            public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer id = rs.getInt("id");
                String title = rs.getString("title");
                String director = rs.getString("directors");
                List<Genre> filmGenres = Arrays.stream(rs.getString("genres").split(";"))
                        .map(Genre::valueOf)
                        .collect(Collectors.toList());
                Integer ageLimit = rs.getInt("age_limit");
                Integer duration = rs.getInt("duration");

                return new Film(id, title, director, filmGenres, ageLimit, duration);
            }
        });

        // Calculate the match score for each film and find the film with the highest score
        Film bestMatch = null;
        double bestMatchScore = -1.0;
        for (Film film : films) {
            long matchingGenresCount = film.genres().stream()
                    .filter(genres::contains)
                    .count();
            double matchScore1 = !genres.isEmpty() ? (double) matchingGenresCount / genres.size() : 0; // score based on watched genres
            double matchScore2 = !film.genres().isEmpty() ? (double) matchingGenresCount / film.genres().size() : 0; // score based on film's genres
            double matchScore = 0.5 * matchScore1 + 0.5 * matchScore2; // weighted average of the two scores
            if (matchScore > bestMatchScore) {
                bestMatch = film;
                bestMatchScore = matchScore;
            }
        }

        return bestMatch;
    }

}
