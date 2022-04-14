package com.jmunoz.bbdd.comics.service;

import com.jmunoz.bbdd.comics.models.Tematica;

import java.sql.SQLException;
import java.util.List;

public interface TematicaService {

    List<Tematica> listar() throws SQLException;

    Tematica porId(Long id) throws SQLException;

    Tematica porNombre(String nombre) throws SQLException;

    Tematica guardar(Tematica tematica) throws SQLException;

    void eliminar(Long id) throws SQLException;
}
