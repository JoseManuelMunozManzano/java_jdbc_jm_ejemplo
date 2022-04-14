package com.jmunoz.bbdd.comics.repository;

import com.jmunoz.bbdd.comics.models.Comic;
import com.jmunoz.bbdd.comics.models.Tematica;
import com.jmunoz.bbdd.comics.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComicRepositoryImpl implements Repository<Comic> {
    private Connection conn;

    public ComicRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    public ComicRepositoryImpl() {
    }

    @Override
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Comic> findAll() throws SQLException {
        List<Comic> comics = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT c.*, u.username, t.nombre" +
                    " FROM comics c" +
                    " INNER JOIN usuarios u ON u.id = c.usuario_id" +
                    " INNER JOIN tematicas t ON t.id = c.tematica_id")) {
            while (rs.next()) {
                Comic comic = crearComic(rs);
                comics.add(comic);
            }
        }

        return comics;
    }

    @Override
    public Comic findByName(String name) throws SQLException {
        Comic comic = null;

        try (PreparedStatement stmt = conn.prepareStatement("SELECT c.*, u.username, t.nombre" +
                " FROM comics c" +
                " INNER JOIN usuarios u ON u.id = c.usuario_id" +
                " INNER JOIN tematicas t ON t.id = c.tematica_id" +
                " WHERE c.nombre = ?")) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    comic = crearComic(rs);
                }
            }
        }

        return comic;
    }

    @Override
    public Comic findById(Long id) throws SQLException {
        Comic comic = null;

        try (PreparedStatement stmt = conn.prepareStatement("SELECT c.*, u.username, t.nombre" +
                " FROM comics c" +
                " INNER JOIN usuarios u ON u.id = c.usuario_id" +
                " INNER JOIN tematicas t ON t.id = c.tematica_id" +
                " WHERE c.id = ?")) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    comic = crearComic(rs);
                }
            }
        }

        return comic;
    }

    @Override
    public Comic save(Comic comic) throws SQLException {
        String sql;
        boolean esUpdate = false;

        if (comic.getId() != null && comic.getId() > 0) {
            esUpdate = true;
            sql = "UPDATE comics SET nombre = ?, precio = ?, fecha_registro = ?, tematica_id = ?, usuario_id = ?" +
                    " WHERE id = ?";
        } else {
            sql = "INSERT INTO comics (nombre, precio, fecha_registro, tematica_id, usuario_id) VALUES (?, ?, ? ,?, ?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, comic.getNombre());
            stmt.setFloat(2, comic.getPrecio());
            stmt.setDate(3, new Date(comic.getFechaRegistro().getTime()));
            stmt.setLong(4, comic.getTematica().getId());
            stmt.setLong(5, comic.getUsuario().getId());
            if (esUpdate) {
                stmt.setLong(6, comic.getId());
            }

            stmt.executeUpdate();

            if (!esUpdate) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        comic.setId(rs.getLong(1));
                    }
                }
            }

            return comic;
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM comics WHERE id = ?")) {
            stmt.setLong(1, id);

            stmt.executeUpdate();
        }
    }

    private Comic crearComic(ResultSet rs) throws SQLException {
        Comic comic = new Comic();

        comic.setId(rs.getLong("id"));
        comic.setNombre(rs.getString("c.nombre"));
        comic.setPrecio(rs.getFloat("precio"));
        comic.setFechaRegistro(rs.getDate("fecha_registro"));

        Tematica tematica = new Tematica();
        tematica.setId(rs.getLong("tematica_id"));
        tematica.setNombre(rs.getString("t.nombre"));
        comic.setTematica(tematica);

        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("usuario_id"));
        usuario.setUsername(rs.getString("username"));
        comic.setUsuario(usuario);

        return comic;
    }
}
