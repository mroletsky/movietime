package com.example.movietime;

import java.time.LocalDateTime;

public record Session(
        LocalDateTime time,
        boolean[][] isSeatFree,
        Film film
) {}
