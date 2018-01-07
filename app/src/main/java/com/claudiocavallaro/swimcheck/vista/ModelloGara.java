package com.claudiocavallaro.swimcheck.vista;

/**
 * Created by claudiocavallaro on 04/01/18.
 */

public class ModelloGara {

    private String gara;
    private String descrizione;
    private String tempo;
    private int image;

    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public ModelloGara(String gara, String descrizione, String tempo){
        this.gara = gara;
        this.descrizione = descrizione;
        this.tempo = tempo;
        this.image = image;
    }

    public String getGara() {
        return gara;
    }

    public void setGara(String gara) {
        this.gara = gara;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }
}
