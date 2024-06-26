package com.example.movietime;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class SessionRepository {

    private final FilmRepository filmRepository;

    private final JdbcClient jdbcClient;

    private List<Session> sessions = new ArrayList<>();

    public SessionRepository(FilmRepository filmRepository, JdbcClient jdbcClient) {
        this.filmRepository = filmRepository;
        this.jdbcClient = jdbcClient;
    }

    public List<Session> findAll() {
        return sessions;
    }

    public Session findById(Integer id) {
        return sessions.stream()
                .filter(session -> session.id().equals(id))
                .findFirst()
                .get();
    }

    public List<Session> findByTimeBetween(LocalDateTime start, LocalDateTime end) {
        return sessions.stream()
                .filter(session -> session.time().isBefore(end) && session.time().isAfter(start))
                .toList();
    }

    public List<Session> findByLanguage(Language language) {
        return sessions.stream()
                .filter(session -> session.language().equals(language))
                .toList();
    }

    public List<Session> findByDateAndLanguage(LocalDateTime start, LocalDateTime end, Language language) {
        return sessions.stream()
                .filter(session -> session.time().isBefore(end) && session.time().isAfter(start))
                .filter(session -> session.language().equals(language))
                .toList();
    }

    @PostConstruct
    private void init() {
        sessions.add(new Session(1,
                LocalDateTime.of(2024, 3, 30, 16, 45),
                SeatsManager.generateSeating(10, 20),
                filmRepository.findById(1),
                Language.ENGLISH
        ));

        sessions.add(new Session(2,
                LocalDateTime.of(2024, 3, 30, 17, 45),
                SeatsManager.generateSeating(15, 25),
                filmRepository.findById(2),
                Language.ESTONIAN
        ));

        sessions.add(new Session(3,
                LocalDateTime.of(2024, 3, 30, 18, 30),
                SeatsManager.generateSeating(20, 20),
                filmRepository.findById(4),
                Language.ESTONIAN
        ));

        sessions.add(new Session(4,
                LocalDateTime.of(2024, 3, 31, 20, 30),
                SeatsManager.generateSeating(20, 25),
                filmRepository.findById(3),
                Language.ENGLISH
        ));

        sessions.add(new Session(5,
                LocalDateTime.of(2024, 3, 31, 18, 15),
                SeatsManager.generateSeating(20, 25),
                filmRepository.findById(5),
                Language.ESTONIAN
        ));

        sessions.add(new Session(6,
                LocalDateTime.of(2024, 4, 1, 19, 15),
                SeatsManager.generateSeating(20, 25),
                filmRepository.findById(4),
                Language.ESTONIAN
        ));

        sessions.add(new Session(7,
                LocalDateTime.of(2024, 4, 1, 18, 15),
                SeatsManager.generateSeating(20, 25),
                filmRepository.findById(5),
                Language.ESTONIAN
        ));

        sessions.add(new Session(8,
                LocalDateTime.of(2024, 4, 2, 21, 30),
                SeatsManager.generateSeating(10, 25),
                filmRepository.findById(1),
                Language.ESTONIAN
        ));

        sessions.add(new Session(9,
                LocalDateTime.of(2024, 4, 1, 15, 0),
                SeatsManager.generateSeating(20, 20),
                filmRepository.findById(2),
                Language.ESTONIAN
        ));

        Collections.sort(sessions, new SessionComparator());
    }

}
