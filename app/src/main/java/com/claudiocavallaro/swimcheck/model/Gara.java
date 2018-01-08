package com.claudiocavallaro.swimcheck.model;

import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * Created by claudiocavallaro on 04/01/18.
 */

public class Gara {

    private String data;
    private String tipo;
    private String tempo;
    private String vasca;
    private String federazione;
    private String categoria;

    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private String citta;

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getVasca() {
        return vasca;
    }

    public void setVasca(String vasca) {
        this.vasca = vasca;
    }

    public String getFederazione() {
        return federazione;
    }

    public void setFederazione(String federazione) {
        this.federazione = federazione;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String toString(){
        return "Gara : " + this.citta + "\nData: " + this.data + "\nTipo: " + this.tipo + "\ntempo: " + this.tempo + "\nVasca: " + this.vasca + "\nfederazione: " + this.federazione + "\n";
    }

    public long toTime(String prova){
        long tot;

        StringTokenizer st = new StringTokenizer(prova, "'");
        Integer min = new Integer(0);
        Integer sec = new Integer(0);
        Integer milli = new Integer(0);
        while(st.hasMoreElements()) {
            min = Integer.parseInt(st.nextElement().toString());
            sec = Integer.parseInt(st.nextElement().toString());
            milli = Integer.parseInt(st.nextElement().toString());

            System.out.println("min " + min + " sec " + sec + " milli " + milli);
        }

        long minL = TimeUnit.MILLISECONDS.toMillis(min);
        long secL = TimeUnit.MILLISECONDS.toMillis(sec);
        tot = minL + secL + milli;

        return tot;
    }
}
