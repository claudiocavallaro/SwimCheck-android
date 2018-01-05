package com.claudiocavallaro.swimcheck.vista;

import java.io.Serializable;

/**
 * Created by claudiocavallaro on 04/01/18.
 */

public class ModelloRicerca implements Serializable{

    private String nome;
    private String societa;
    private String anno;

    public ModelloRicerca(String nome, String societa, String anno){
        this.nome = nome;
        this.societa = societa;
        this.anno = anno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSocieta() {
        return societa;
    }

    public void setSocieta(String societa) {
        this.societa = societa;
    }

    public String getAnno() {
        return anno;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }
}
