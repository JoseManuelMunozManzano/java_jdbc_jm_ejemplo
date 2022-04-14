package com.jmunoz.bbdd.comics.repository;

import com.jmunoz.bbdd.comics.models.Tematica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TematicaRepositoryImpl implements Repository<Tematica> {
    Connection conn;

    public TematicaRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    public TematicaRepositoryImpl() {
    }

    @Override
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Tematica> findAll() throws SQLException {
        List<Tematica> tematicas = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tematicas")) {

            while (rs.next()) {
                Tematica tematica = crearTematica(rs);
                tematicas.add(tematica);
            }
        }

        return tematicas;
    }

    @Override
    public Tematica findById(Long id) throws SQLException {
        Tematica tematica = null;

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tematicas WHERE id = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tematica = crearTematica(rs);
                }
            }
        }

        return tematica;
    }

    @Override
    public Tematica findByName(String nombre) throws SQLException {
        Tematica tematica = null;

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tematicas WHERE nombre = ?")) {
            stmt.setString(1, nombre);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tematica = crearTematica(rs);
                }
            }
        }

        return tematica;
    }

    @Override
    public Tematica save(Tematica tematica) throws SQLException {
        String sql;
        boolean esUpdate = false;

        if (tematica.getId() != null && tematica.getId() > 0) {
            sql = "UPDATE tematicas SET nombre = ? WHERE id = ?";
            esUpdate = true;
        } else {
            sql = "INSERT INTO tematicas (NOMBRE) VALUES (?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tematica.getNombre());
            if (esUpdate) {
                stmt.setLong(2, tematica.getId());
            }

            stmt.executeUpdate();

            if (!esUpdate) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        tematica.setId(rs.getLong(1));
                    }
                }
            }
        }

        return tematica;
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM tematicas WHERE id = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Tematica crearTematica(ResultSet rs) throws SQLException {
        Tematica t = new Tematica();
        t.setId(rs.getLong("id"));
        t.setNombre(rs.getString("nombre"));

        return t;
    }
}
