package com.example.movietime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmRepository filmRepository;

    public FilmController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping("")
    List<Film> findAll() {
        return filmRepository.findAll();
    }

    @GetMapping("/{id}")
    Film findById(@PathVariable Integer id) {
        Optional<Film> film = filmRepository.findById(id);
        if(film.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return film.get();
    }

    @GetMapping("/genre/{input}")
    List<Film> findByGenre(@PathVariable String input) {
        Genre genre = Genre.valueOf(input.toUpperCase());
        return filmRepository.findByGenre(genre);
    }

    @GetMapping("/age/{age}")
    List<Film> findByGenre(@PathVariable Integer age) {
        return filmRepository.findByAgeLimit(age);
    }

    @GetMapping("/home")
    String home() {
        return "Homepage";
    }

}
