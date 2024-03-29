package com.example.movietime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
        Optional<Film> film = filmRepository.findById(id);
        if(film.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(film.get(), HttpStatus.OK);
    }

    @GetMapping("/genre/{input}")
    public ResponseEntity<?> findByGenre(@PathVariable String input) {
        try {
            Genre genre = Genre.valueOf(input.toUpperCase());
            return new ResponseEntity<>(filmRepository.findByGenre(genre), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<?> findByGenre(@PathVariable Integer age) {
        if (age < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(filmRepository.findByAgeLimit(age), HttpStatus.OK);
    }

    @GetMapping("/home")
    String home() {
        return "Homepage";
    }

}
