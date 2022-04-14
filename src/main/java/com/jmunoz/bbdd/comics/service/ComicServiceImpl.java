package com.jmunoz.bbdd.comics.service;

import com.jmunoz.bbdd.comics.models.Comic;
import com.jmunoz.bbdd.comics.models.Propietario;
import com.jmunoz.bbdd.comics.models.Tematica;
import com.jmunoz.bbdd.comics.models.Usuario;
import com.jmunoz.bbdd.comics.repository.ComicRepositoryImpl;
import com.jmunoz.bbdd.comics.repository.Repository;
import com.jmunoz.bbdd.comics.repository.TematicaRepositoryImpl;
import com.jmunoz.bbdd.comics.repository.UserRepositoryImpl;
import com.jmunoz.bbdd.comics.utils.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ComicServiceImpl implements ComicService {
    private Repository<Comic> comicRepository;
    private Repository<Tematica> tematicaRepository;
    private Repository<Usuario> usuarioRepository;

    public ComicServiceImpl() {
        comicRepository = new ComicRepositoryImpl();
        tematicaRepository = new TematicaRepositoryImpl();
        usuarioRepository = new UserRepositoryImpl();
    }

    @Override
    public List<Comic> listar() throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            comicRepository.setConn(conn);

            return comicRepository.findAll();
        }
    }

    @Override
    public Comic porId(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            comicRepository.setConn(conn);

            return comicRepository.findById(id);
        }
    }

    @Override
    public Comic porNombre(String nombre) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            comicRepository.setConn(conn);

            return comicRepository.findByName(nombre);
        }
    }

    @Override
    public Comic guardar(Propietario propietario) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            comicRepository.setConn(conn);
            tematicaRepository.setConn(conn);
            usuarioRepository.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            Comic nuevoComic = null;
            try {
                Tematica nuevaTematica = null;
                Usuario nuevoUsuario = null;

                if (propietario.getTematica().getId() == null || propietario.getTematica().getId() <= 0) {
                    String nombreTematica = propietario.getTematica().getNombre();
                    nuevaTematica = tematicaRepository.findByName(nombreTematica);

                    if (nuevaTematica == null) {
                        nuevaTematica = tematicaRepository.save(propietario.getTematica());
                    }

                    propietario.setTematica(nuevaTematica);
                }

                if (propietario.getUsuario().getId() == null || propietario.getUsuario().getId() <= 0) {
                    String username = propietario.getUsuario().getUsername();
                    nuevoUsuario = usuarioRepository.findByName(username);

                    if (nuevoUsuario == null) {
                        nuevoUsuario = usuarioRepository.save(propietario.getUsuario());
                    }

                    propietario.setUsuario(usuarioRepository.findByName(username));
                }

                propietario.getComic().setTematica(nuevaTematica);
                propietario.getComic().setUsuario(nuevoUsuario);
                nuevoComic = comicRepository.save(propietario.getComic());

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

            return nuevoComic;
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            comicRepository.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {
                comicRepository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
}
