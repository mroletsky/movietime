package com.example.movietime;

import java.util.Comparator;

public class SessionComparator implements Comparator<Session> {
    @Override
    public int compare(Session s1, Session s2) {
        return s1.time().compareTo(s2.time());
    }
}
