package com.matt.Libreria;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;

//
@JsonIncludeProperties({"autore","anno_nascita"})
public class Autore {
    private String autore;
    private int id;

    public int getAnno_nascita() {
        return anno_nascita;
    }

    public void setAnno_nascita(int anno_nascita) {
        this.anno_nascita = anno_nascita;
    }
    private int anno_nascita;

    // Initialization
    protected Autore() {};

    protected Autore(String autore){
        this.autore=autore;
    }

    // Getter and setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

}
