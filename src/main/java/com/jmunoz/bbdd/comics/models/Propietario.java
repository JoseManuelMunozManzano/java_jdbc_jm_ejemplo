package com.jmunoz.bbdd.comics.models;

public class Propietario {

    private Comic comic;
    private Tematica tematica;
    private Usuario usuario;

    public Propietario() {
    }

    public Propietario(Comic comic, Tematica tematica, Usuario usuario) {
        this.comic = comic;
        this.tematica = tematica;
        this.usuario = usuario;
    }

    public Comic getComic() {
        return comic;
    }

    public void setComic(Comic comic) {
        this.comic = comic;
    }

    public Tematica getTematica() {
        return tematica;
    }

    public void setTematica(Tematica tematica) {
        this.tematica = tematica;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Propietario{" +
                "comic=" + comic +
                ", tematica=" + tematica +
                ", usuario=" + usuario +
                '}';
    }
}
