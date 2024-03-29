package com.example.movietime;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository {
    private List<Film> films = new ArrayList<>();

    List<Film> findAll() {
        return films;
    }

    Optional<Film> findById(Integer id) {
        return films.stream()
                .filter(film -> film.id().equals(id))
                .findFirst();
    }

    List<Film> findByGenre(Genre genre) {
        return films.stream()
                .filter(film -> film.genres().contains(genre))
                .toList();
    }

    List<Film> findByAgeLimit(int ageLimit) {
        return films.stream()
                .filter(film -> film.ageLimit().compareTo(ageLimit) >= 0)
                .toList();
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
}
