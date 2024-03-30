package com.example.movietime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionRepository sessionRepository;

    public SessionController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(sessionRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(sessionRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<?> findByDate(@PathVariable("date") String dateString) {
        LocalDate date = LocalDate.parse(dateString);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        return new ResponseEntity<>(sessionRepository.findByTimeBetween(start, end), HttpStatus.OK);
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<?> findByLanguage(@PathVariable("language") String languageString) {
        try {
            Language language = Language.valueOf(languageString.toUpperCase());
            return new ResponseEntity<>(sessionRepository.findByLanguage(language), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/date/{date}/language/{language}")
    public ResponseEntity<?> findByDateAndLanguage(@PathVariable("date") String dateString, @PathVariable("language") String languageString) {
        try {
            Language language = Language.valueOf(languageString.toUpperCase());
            LocalDate date = LocalDate.parse(dateString);
            LocalDateTime start = date.atStartOfDay();
            LocalDateTime end = date.plusDays(1).atStartOfDay();
            return new ResponseEntity<>(sessionRepository.findByDateAndLanguage(start, end, language), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{id}/recommend-seats")
    public ResponseEntity<?> findBestSeats(@PathVariable Integer id, @RequestParam int numSeats) {
        Session session = sessionRepository.findById(id);
        return new ResponseEntity<>(SeatsManager.findBestSeats(session.isSeatFree(), numSeats), HttpStatus.OK);
    }

}
