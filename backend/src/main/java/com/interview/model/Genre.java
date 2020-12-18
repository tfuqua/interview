package com.interview.model;

public enum Genre {
    ADVENTURE("Adventure"),
    ANIMATION("Animation"),
    CHILDREN("Children"),
    COMEDY("Comedy"),
    FANTASY("Fantasy"),
    ROMANCE("Romance"),
    DRAMA("Drama"),
    ACTION("Action"),
    CRIME("Crime"),
    THRILLER("Thriller"),
    HORROR("Horror"),
    MYSTERY("Mystery"),
    DOCUMENTARY("Documentary"),
    IMAX("IMAX"),
    MUSICAL("Musical"),
    WAR("War");

    private final String value;

    Genre(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

}
