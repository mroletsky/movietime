package com.example.movietime;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FilmRepository {
    private List<Film> films = new ArrayList<>();
    private List<Film> watchHistory = new ArrayList<>();

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public FilmRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // I asked GPT for an example request to the database and made these find methods based on that
    // Since GPT gave me and example using a JdbcTemplate instead of the newer JdbcClient, I used that instead
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

    void addToWatchHistory(Film film) {
        watchHistory.add(film);
    }

    List<Film> recommendedFilms() {
        return null;
    }

    @PostConstruct
    private void init() {
        films.add(new Film(1,
                "Düün: teine osa",
                "Denis Villeneuve",
                List.of(Genre.SCIFI, Genre.ADVENTURE),
                12,
                166));

        films.add(new Film(2,
                "Kung Fu Panda 4",
                "Mike Mitchell, Stephanie Strine",
                List.of(Genre.ANIMATION, Genre.ACTION, Genre.ADVENTURE),
                0,
                94));

        films.add(new Film(3,
                "Elu ja armastus",
                "Helen Takkin",
                List.of(Genre.DRAMA),
                12,
                118));

        films.add(new Film(4,
                "Halastamatu maa",
                "William Eubank",
                List.of(Genre.ACTION, Genre.THRILLER),
                12,
                113));

        films.add(new Film(5,
                "Kass ja koer: põgenemine",
                "Reem Kherici",
                List.of(Genre.COMEDY, Genre.ADVENTURE),
                6,
                87));
    }


    public List<Genre> convertToGenres(String genresStr) {
        String[] genresArr = genresStr.split(";");
        List<Genre> genres = new ArrayList<>();
        for (String genreStr : genresArr) {
            Genre genre = Genre.valueOf(genreStr);
            genres.add(genre);
        }
        return genres;
    }
}
