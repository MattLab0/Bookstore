package com.matt.bookstore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

//
@JsonIncludeProperties({"id","author","birth_year"})
public class Author {
    private int id;
    private String author;
    private int birth_year;

    // Initialization
    protected Author() {};

    protected Author(String author, int birth_year){
        this.author = author;
        this.birth_year = birth_year;
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

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

}
