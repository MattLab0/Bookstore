package com.matt.bookstore;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIncludeProperties({"id", "title", "author", "page_number", "publication_year", "genre"})
@JsonPropertyOrder({"id", "title", "author", "page_number", "publication_year", "genre"})
public class Book {
    private int id;
    private String title;
    private int page_number;
    private int publication_year;

    private String name;
    private String surname;
    private String author;
    private String genre;


    protected Book(String title, String name, String surname, int page_number, String genre, int publication_year){
        this.title = title;
        this.author = name + " " + surname;
        this.surname = surname;
        this.name = name;
        this.page_number = page_number;
        this.genre = genre;
        this.publication_year = publication_year;  
    }
    
    protected Book(String title, String author, String genre, int page_number, int publication_year){
        this.title = title;
        this.author = author;
        this.page_number = page_number;
        this.genre = genre;
        this.publication_year = publication_year;  
    }
    
    protected Book(int id, String title, String author, String genre, int page_number, int publication_year){
        this.id = id;
        this.title = title;
        this.author = author;
        this.page_number = page_number;
        this.genre = genre;
        this.publication_year = publication_year;  
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPage_number() {
        return page_number;
    }

    public void setPage_number(int page_number) {
        this.page_number = page_number;
    }

    public String getAuthor() {
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


    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publication_year) {
        this.publication_year = publication_year;
    }
}
