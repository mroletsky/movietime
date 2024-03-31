package com.example.movietime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class FilmRepositoryTest {

    @InjectMocks
    private FilmRepository repository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindAll() {
        Film film = new Film(1, "Test Title", "Test Director", List.of(Genre.ACTION), 12, 120);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(film));

        List<Film> result = repository.findAll();

        assertEquals(1, result.size());
        assertEquals(film, result.getFirst());
    }

}