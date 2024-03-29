package com.example.movietime;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SessionRepository {

    private final FilmRepository filmRepository;

    private List<Session> sessions = new ArrayList<>();

    public SessionRepository(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    List<Session> findAll() {
        return sessions;
    }

    @PostConstruct
    private void init() {
        sessions.add(new Session(1,
                LocalDateTime.of(2024, 3, 30, 16, 45),
                SeatsManager.generateSeating(10, 15),
                filmRepository.findById(1).get(),
                Language.ENGLISH
        ));

        sessions.add(new Session(2,
                LocalDateTime.of(2024, 3, 30, 17, 45),
                SeatsManager.generateSeating(10, 15),
                filmRepository.findById(2).get(),
                Language.ENGLISH
        ));

        sessions.add(new Session(3,
                LocalDateTime.of(2024, 3, 30, 18, 30),
                SeatsManager.generateSeating(10, 15),
                filmRepository.findById(4).get(),
                Language.ENGLISH
        ));
    }

}
