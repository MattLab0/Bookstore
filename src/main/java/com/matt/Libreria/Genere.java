package com.matt.Libreria;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

@JsonIncludeProperties ({"genere"})
public class Genere {

    private String genere;
    public int id;

    protected Genere(){};
    protected Genere(String genere){
        this.genere=genere;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }


}
