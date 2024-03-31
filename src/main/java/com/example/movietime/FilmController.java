package com.example.movietime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmRepository filmRepository;

    public FilmController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(filmRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            Film film = filmRepository.findById(id);
            return new ResponseEntity<>(film, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<?> findByGenre(@PathVariable("genre") String input) {
        try {
            Genre genre = Genre.valueOf(input.toUpperCase());
            return new ResponseEntity<>(filmRepository.findByGenre(genre), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/genres")
    public ResponseEntity<Genre[]> getAllGenres() {
        Genre[] genres = Genre.values();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<?> findByGenre(@PathVariable Integer age) {
        if (age < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(filmRepository.findByAgeLimit(age), HttpStatus.OK);
    }

    @GetMapping("/genre/{genre}/age/{age}")
    public ResponseEntity<?> findByGenre(@PathVariable("genre") String input, @PathVariable Integer age) {
        if (age < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        try {
            Genre genre = Genre.valueOf(input.toUpperCase());
            return new ResponseEntity<>(filmRepository.findByGenreAndAgeLimit(genre, age), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/recommend")
    public ResponseEntity<?> recommend() {
        return new ResponseEntity<>(filmRepository.recommendFilm(), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/watch")
    void watchFilm(@RequestBody Film film) {
        filmRepository.addFilmToWatchHistory(film);
    }

}
