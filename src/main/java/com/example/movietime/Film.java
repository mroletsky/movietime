package com.example.movietime;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record Film(

        Integer id,
        @NotEmpty
        String title,
        String director,
        List<Genre> genres,
        @PositiveOrZero
        Integer ageLimit,
        @Positive
        Integer duration // in minutes
) {}
