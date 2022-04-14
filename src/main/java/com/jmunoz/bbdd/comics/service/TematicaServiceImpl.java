package com.jmunoz.bbdd.comics.service;

import com.jmunoz.bbdd.comics.models.Tematica;
import com.jmunoz.bbdd.comics.repository.Repository;
import com.jmunoz.bbdd.comics.repository.TematicaRepositoryImpl;
import com.jmunoz.bbdd.comics.utils.ConexionBaseDatos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TematicaServiceImpl implements TematicaService {
    private Repository<Tematica> tematicaRepository;

    public TematicaServiceImpl() {
        tematicaRepository = new TematicaRepositoryImpl();
    }

    @Override
    public List<Tematica> listar() throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            tematicaRepository.setConn(conn);

            return tematicaRepository.findAll();
        }
    }

    @Override
    public Tematica porId(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            tematicaRepository.setConn(conn);

            return tematicaRepository.findById(id);
        }
    }

    @Override
    public Tematica porNombre(String nombre) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            tematicaRepository.setConn(conn);

            return tematicaRepository.findByName(nombre);
        }
    }

    @Override
    public Tematica guardar(Tematica tematica) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            tematicaRepository.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            Tematica nuevaTematica = null;
            try {
                nuevaTematica = tematicaRepository.save(tematica);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }

            return nuevaTematica;
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        try (Connection conn = ConexionBaseDatos.getConnection()) {
            tematicaRepository.setConn(conn);

            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }

            try {
                tematicaRepository.delete(id);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        }
    }
}
