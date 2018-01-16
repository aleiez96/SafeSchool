package com.example.alessio.safeschool;

/**
 * Created by alessio on 16/01/2018.
 */

public class Scuole {

    String nome;
    String id;
    String regione;
    String provincia;
    String latitudine;
    String longitudine;
    String sito;
    String indirizzo;
    String indirizzo_email;

    public Scuole(String s, String s1, String s2, String s3, String s4, String s5) {
        nome=s;
        id=s1;
        regione=s2;
        provincia=s3;
        latitudine=s4;
        longitudine=s5;
    }

    public Scuole(String s, String s1) {
        nome=s;
        id=s1;
    }

    public Scuole(String s,String s1,String s2) {
        sito=s;
        indirizzo=s1;
        indirizzo_email=s2;
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
        return id;
    }

    public String getRegione() {
        return regione;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getLatitudine() {
        return latitudine;
    }

    public String getLongitudine() {
        return longitudine;
    }



    public String getNome() {
        return nome;
    }
}
