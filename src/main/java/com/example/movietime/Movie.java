package com.example.movietime;

public record Movie(
        String title,
        String director,
        Genre genre,
        int Duration // in seconds
) {}
