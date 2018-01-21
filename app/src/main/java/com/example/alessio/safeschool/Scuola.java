package com.example.alessio.safeschool;

/**
 * Created by alessio on 16/01/2018.
 */

public class Scuola {

    private String nome;
    private String id;
    private String regione;
    private String provincia;
    private String latitudine;
    private String longitudine;
    private String sito;
    private String indirizzo;
    private String indirizzo_email;

    public Scuola() {
    }

    public Scuola(String nome, String id, String regione, String provincia, String latitudine, String longitudine) {
        this.nome=nome;
        this.id=id;
        this.regione=regione;
        this.provincia=provincia;
        this.latitudine=latitudine;
        this.longitudine=longitudine;
    }

    public Scuola(String nome, String id) {
        this.nome=nome;
        this.id=id;
    }

    public Scuola(String sito, String indirizzo, String indirizzo_email) {
        this.sito=sito;
        this.indirizzo=indirizzo;
        this.indirizzo_email=indirizzo_email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setLatitudine(String latitudine) {
        this.latitudine = latitudine;
    }

    public void setLongitudine(String longitudine) {
        this.longitudine = longitudine;
    }


    public String getId() {
        return this.id;
    }

    public String getRegione() {
        return this.regione;
    }

    public String getProvincia() {
        return this.provincia;
    }

    public String getLatitudine() {
        return this.latitudine;
    }

    public String getLongitudine() {
        return this.longitudine;
    }



    public String getNome() {
        return this.nome;
    }
}
