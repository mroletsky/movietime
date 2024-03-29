package com.example.movietime;

import java.time.LocalDateTime;

public record Session(
        Integer id,
        LocalDateTime time,
        boolean[][] isSeatFree,
        Film film,
        Language language
) {}
