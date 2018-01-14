package com.claudiocavallaro.swimcheck.vista;

/**
 * Created by claudiocavallaro on 14/01/18.
 */

public class ModelloConfronto {

    private String title;

    private String nome;
    private String tempo;

    private boolean gruppo = false;

    public ModelloConfronto(String title){
        this.setTitle(title);
        this.setGruppo(true);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public boolean isGruppo() {
        return gruppo;
    }

    public void setGruppo(boolean gruppo) {
        this.gruppo = gruppo;
    }

    public ModelloConfronto(String title, String nome, String tempo){
        super();
        this.setTitle(title);
        this.setNome(nome);
        this.setTempo(tempo);
    }
}
