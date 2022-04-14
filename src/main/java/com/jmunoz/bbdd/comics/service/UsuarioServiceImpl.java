package com.jmunoz.bbdd.comics.service;

import com.jmunoz.bbdd.comics.models.Usuario;
import com.jmunoz.bbdd.comics.repository.Repository;
import com.jmunoz.bbdd.comics.repository.UserRepositoryImpl;
import com.jmunoz.bbdd.comics.utils.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {
    private Repository<Usuario> usuarioRepository;

    public UsuarioServiceImpl() {
        usuarioRepository = new UserRepositoryImpl();
    }

    @Override
    public List<Usuario> listar() throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            usuarioRepository.setConn(conn);

            return usuarioRepository.findAll();
        }
    }

    @Override
    public Usuario porId(Long id) throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            usuarioRepository.setConn(conn);

            return usuarioRepository.findById(id);
        }
    }

    @Override
    public Usuario porUsername(String username) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            usuarioRepository.setConn(conn);

            return usuarioRepository.findByName(username);
        }
    }

    @Override
    public Usuario guardar(Usuario usuario) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            usuarioRepository.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            Usuario nuevoUsuario = new Usuario();
            try {
                nuevoUsuario = usuarioRepository.save(usuario);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

            return nuevoUsuario;
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try(Connection conn = ConexionBaseDatos.getConnection()) {
            usuarioRepository.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {
                usuarioRepository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
}
