package com.matt.bookstore;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;

@JsonIncludeProperties({"title","author","genre"})
@JsonPropertyOrder({"title","author","genre"})
public class Book {
    private int id;
    private String title;
    private int page_number;
    private LocalDateTime publication_year;

    private String name;
    private String surname;
    private String author;
    private String genre;


    protected Book(String title, String name, String surname, int page_number, String genre){
        this.title = title;
        this.author = name + " " + surname;
        this.surname=surname;
        this.name = name;
        this.page_number=page_number;
        this.genre=genre;
    }

    protected Book(String title, String author, String genre){
        this.title = title;
        this.author = author;
        this.publication_year = publication_year;
        this.page_number = page_number;
        this.genre = genre;
    }
    protected Book(int id, String title, String author, String genre){
        this.id = id;
        this.title = title;
        this.author = author;
        this.publication_year = publication_year;
        this.page_number = page_number;
        this.genre = genre;
    }

    protected Book(){}

    @Override
    public String toString(){
        return "Book [" + title +", " + author + ", " + genre +"]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public int getpage_number() {
        return page_number;
    }

    public void setpage_number(int page_number) {
        this.page_number = page_number;
    }

    public String getAutore() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    public LocalDateTime getPublicYear() {
        return publication_year;
    }

    public void setPublicYear(LocalDateTime publication_year) {
        this.publication_year = publication_year;
    }
}
