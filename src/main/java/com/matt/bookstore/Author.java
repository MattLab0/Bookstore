package com.matt.bookstore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

//
@JsonIncludeProperties({"author","birth_year"})
public class Author {
    private String author;
    private int id;

    public int getAnno_nascita() {
        return birth_year;
    }

    public void setAnno_nascita(int birth_year) {
        this.birth_year = birth_year;
    }
    private int birth_year;

    // Initialization
    protected Author() {};

    protected Author(String author){
        this.author=author;
    }

    // Getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

}
