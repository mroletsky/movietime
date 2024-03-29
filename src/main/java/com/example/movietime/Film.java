package com.example.movietime;

import java.util.List;

public record Film(
        Integer id,
        String title,
        List<String> directors,
        List<Genre> genres,
        Integer ageLimit,
        Integer duration // in minutes
) {}
