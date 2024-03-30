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

    Session findById(Integer id) {
        return sessions.stream()
                .filter(session -> session.id().equals(id))
                .findFirst()
                .get();
    }

    List<Session> findByTimeBetween(LocalDateTime start, LocalDateTime end) {
        return sessions.stream()
                .filter(session -> session.time().isBefore(end) && session.time().isAfter(start))
                .toList();
    }

    List<Session> findByLanguage(Language language) {
        return sessions.stream()
                .filter(session -> session.language().equals(language))
                .toList();
    }

    List<Session> findByDateAndLanguage(LocalDateTime start, LocalDateTime end, Language language) {
        return sessions.stream()
                .filter(session -> session.time().isBefore(end) && session.time().isAfter(start))
                .filter(session -> session.language().equals(language))
                .toList();
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
                SeatsManager.generateSeating(20, 25),
                filmRepository.findById(2).get(),
                Language.ESTONIAN
        ));

        sessions.add(new Session(3,
                LocalDateTime.of(2024, 3, 30, 18, 30),
                SeatsManager.generateSeating(20, 25),
                filmRepository.findById(4).get(),
                Language.ESTONIAN
        ));

        sessions.add(new Session(4,
                LocalDateTime.of(2024, 3, 31, 20, 30),
                SeatsManager.generateSeating(20, 25),
                filmRepository.findById(3).get(),
                Language.ENGLISH
        ));

        sessions.add(new Session(5,
                LocalDateTime.of(2024, 3, 31, 18, 15),
                SeatsManager.generateSeating(20, 25),
                filmRepository.findById(5).get(),
                Language.ESTONIAN
        ));
    }

}
