package com.example.alessio.safeschool;

import java.util.Calendar;

/**
 * Created by filippo on 21/01/18.
 */

public class Preferito
{
    private int id;     // inserito solo per simulare struttura tabella a DB
    private String id_scuola = null;
    private String data_inserimento = null;
    private String descrizione = null;

    public Preferito ()
    {
    }

    public Preferito (String id_scuola, String data_inserimento, String descrizione)
    {
        this.id_scuola = id_scuola;
        this.data_inserimento = data_inserimento;
        this.descrizione = descrizione;
    }

    public String getId_scuola() {
        return this.id_scuola;
    }

    public String getData_inserimento() {
        return this.data_inserimento;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setId_scuola(String id_scuola) {
        this.id_scuola = id_scuola;
    }

    public void setData_inserimento(String data_inserimento) {
        this.data_inserimento = data_inserimento;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
