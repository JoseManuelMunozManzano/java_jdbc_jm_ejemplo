package com.jmunoz.bbdd.comics.models;

import java.util.Date;

public class Comic {
    private Long id;
    private String nombre;
    private Float precio;
    private Date fechaRegistro;
    private Tematica tematica;
    private Usuario usuario;

    public Comic() {
    }

    public Comic(Long id, String nombre, Float precio, Date fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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
        return "Comic{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", fechaRegistro=" + fechaRegistro +
                ", tematica=" + tematica +
                ", usuario=" + usuario +
                '}';
    }
}
