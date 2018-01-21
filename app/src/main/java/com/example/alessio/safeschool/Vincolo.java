package com.example.alessio.safeschool;

/**
 * Created by filippo on 21/01/18.
 */

public class Vincolo
{
    private int anno;     // inserito solo per simulare struttura tabella a DB
    private String id_scuola = null;
    private String id_edificio = null;
    private boolean vincoli_idrogeologici;
    private boolean vincoli_paesaggio;
    private boolean edificio_vetusto;
    private String zona_sismica = null;
    private boolean progettazione_antisismica;

    public Vincolo()
    {
    }

    public Vincolo(String id_scuola, String id_edificio, String vincoli_idrogeologici, String vincoli_paesaggio,
                   String edificio_vetusto, String zona_sismica, String progettazione_antisismica)
    {
        this.id_scuola = id_scuola;
        this.id_edificio = id_edificio;
        this.vincoli_idrogeologici = this.stringToBoolean(vincoli_idrogeologici);
        this.vincoli_paesaggio = this.stringToBoolean(vincoli_paesaggio);
        this.edificio_vetusto = this.stringToBoolean(edificio_vetusto);
        this.zona_sismica = zona_sismica;
        this.progettazione_antisismica = this.stringToBoolean(progettazione_antisismica);
    }

    private boolean stringToBoolean(String s){
        if (s.equals("SI") || s.equals("si") || s.equals("Si")){
            return true;
        }
        else {
            return false;
        }
    }

    public String getId_scuola() {
        return this.id_scuola;
    }

    public String getId_edificio() {
        return this.id_edificio;
    }

    public boolean isVincoli_idrogeologici() {
        return this.vincoli_idrogeologici;
    }

    public boolean isVincoli_paesaggio() {
        return this.vincoli_paesaggio;
    }

    public boolean isEdificio_vetusto() {
        return this.edificio_vetusto;
    }

    public String getZona_sismica() {
        return this.zona_sismica;
    }

    public boolean isProgettazione_antisismica() {
        return this.progettazione_antisismica;
    }

    public void setId_scuola(String id_scuola) {
        this.id_scuola = id_scuola;
    }

    public void setId_edificio(String id_edificio) {
        this.id_edificio = id_edificio;
    }

    public void setVincoli_idrogeologici(boolean vincoli_idrogeologici) {
        this.vincoli_idrogeologici = vincoli_idrogeologici;
    }

    public void setVincoli_idrogeologici(String vincoli_idrogeologici) {
        this.vincoli_idrogeologici = this.stringToBoolean(vincoli_idrogeologici);
    }

    public void setVincoli_paesaggio(boolean vincoli_paesaggio) {
        this.vincoli_paesaggio = vincoli_paesaggio;
    }

    public void setVincoli_paesaggio(String vincoli_paesaggio) {
        this.vincoli_paesaggio = this.stringToBoolean(vincoli_paesaggio);
    }

    public void setEdificio_vetusto(boolean edificio_vetusto) {
        this.edificio_vetusto = edificio_vetusto;
    }

    public void setEdificio_vetusto(String edificio_vetusto) {
        this.edificio_vetusto = this.stringToBoolean(edificio_vetusto);
    }

    public void setZona_sismica(String zona_sismica) {
        this.zona_sismica = zona_sismica;
    }

    public void setProgettazione_antisismica(boolean progettazione_antisismica) {
        this.progettazione_antisismica = progettazione_antisismica;
    }

    public void setProgettazione_antisismica(String progettazione_antisismica) {
        this.progettazione_antisismica = this.stringToBoolean(progettazione_antisismica);
    }
}
