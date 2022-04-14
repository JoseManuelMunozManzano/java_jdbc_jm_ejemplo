package com.jmunoz.bbdd.comics.service;

import com.jmunoz.bbdd.comics.models.Usuario;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioService {
    List<Usuario> listar() throws SQLException;

    Usuario porId(Long id) throws SQLException;

    Usuario porUsername(String username) throws SQLException;

    Usuario guardar(Usuario usuario) throws SQLException;

    void eliminar(Long id) throws SQLException;
}
