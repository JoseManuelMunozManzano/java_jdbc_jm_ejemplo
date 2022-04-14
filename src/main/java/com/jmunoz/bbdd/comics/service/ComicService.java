package com.jmunoz.bbdd.comics.service;

import com.jmunoz.bbdd.comics.models.Comic;
import com.jmunoz.bbdd.comics.models.Propietario;
import com.jmunoz.bbdd.comics.models.Tematica;
import com.jmunoz.bbdd.comics.models.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface ComicService {
    List<Comic> listar() throws SQLException;

    Comic porId(Long id) throws SQLException;

    Comic porNombre(String nombre) throws SQLException;

    Comic guardar(Propietario propietario) throws SQLException;

    void eliminar(Long id) throws SQLException;
}
