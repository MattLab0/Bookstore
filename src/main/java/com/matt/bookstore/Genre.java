package com.matt.bookstore;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

@JsonIncludeProperties ({"genre"})
public class Genre {

    private String genre;
    public int id;

    protected Genre(){};
    
    protected Genre(String genre){
        this.genre=genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}
