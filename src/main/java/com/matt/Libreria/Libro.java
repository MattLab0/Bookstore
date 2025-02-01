package com.matt.Libreria;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonIncludeProperties({"titolo","autore","genere"})
@JsonPropertyOrder({"titolo","autore","genere"})
public class Libro {
    private int id;
    private String Titolo;
    private int numeroPagine;
    private LocalDateTime annoPub;

    private String nome;
    private String cognome;
    private String autore;
    private String genere;


    protected Libro(String titolo, String nome, String cognome, int numeroPagine, String genere){
        this.Titolo =titolo;
        this.autore=nome + " " + cognome;
        this.cognome=cognome;
        this.nome=nome;
        this.numeroPagine=numeroPagine;
        this.genere=genere;
    }

    protected Libro(String titolo, String autore, String genere){
        this.Titolo =titolo;
        this.autore = autore;
        this.annoPub =annoPub;
        this.numeroPagine=numeroPagine;
        this.genere=genere;
    }
    protected Libro(int id, String titolo, String autore, String genere){
        this.id =id;
        this.Titolo =titolo;
        this.autore = autore;
        this.annoPub =annoPub;
        this.numeroPagine=numeroPagine;
        this.genere=genere;
    }

    protected Libro(){}

    @Override
    public String toString(){
        return "Libro [" + Titolo +", " + autore + ", " + genere +"]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return Titolo;
    }

    public void setTitolo(String titolo) {
        this.Titolo = titolo;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    public void setNumeroPagine(int numeroPagine) {
        this.numeroPagine = numeroPagine;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }


    public LocalDateTime getAnnoPub() {
        return annoPub;
    }

    public void setAnnoPub(LocalDateTime annoPub) {
        this.annoPub = annoPub;
    }
}
