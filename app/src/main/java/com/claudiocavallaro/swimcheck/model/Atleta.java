package com.claudiocavallaro.swimcheck.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * Created by claudiocavallaro on 03/01/18.
 */

public class Atleta {

    private String nome;
    private String anno;
    private String soc;
    private String sesso;


    private String url;

    public String getUrl() {
        return url;
    }

    private ArrayList<Gara> listaGare = new ArrayList<>();
    private ArrayList<Gara> listBest = new ArrayList<>();

    public ArrayList<Gara> getListBest() {
        return listBest;
    }

    public void setListBest(ArrayList<Gara> listBest) {
        this.listBest = listBest;
    }

    public ArrayList<Gara> getListaGare() {
        return listaGare;
    }

    public ArrayList<Gara> cercaGare(String gara) {
        ArrayList<Gara> listaAppoggio = new ArrayList<>();
        for (Gara g : listaGare) {
            if (g.getTipo().equals(gara)) {
                listaAppoggio.add(g);
            }
        }
        return listaAppoggio;
    }


    public ArrayList<String> cercaTipiGara(){
        ArrayList<String> tipiGare = new ArrayList<>();
        for (Gara gara : listaGare){
            if (tipiGare.contains(gara.getTipo())){
                //do nothing
            } else{
                tipiGare.add(gara.getTipo());
            }
        }
        return tipiGare;
    }


    public void best(){
        ArrayList<String> listaTipi = cercaTipiGara();
        for (int i = 0 ; i < listaTipi.size(); i++){
            ArrayList<Gara> listaGare = cercaGare(listaTipi.get(i));
            Gara min = listaGare.get(0);
            for (Gara gara : listaGare){
                if (gara.getTime() < min.getTime()){
                    min = gara;
                }
            }
            listBest.add(min);
        }
    }


    public void setListaGare(ArrayList<Gara> listaGare) {
        this.listaGare = listaGare;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getSoc() {
        return soc;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String toString() {
        return "Atleta : \nNome " + nome + "\nAnno di nascita: " + anno + "\nSocietà: " + soc + "\nSesso: " + sesso + "\n";
    }

}
