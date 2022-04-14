package com.jmunoz.bbdd.comics.repository;

import com.jmunoz.bbdd.comics.models.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements Repository<Usuario> {
    private Connection conn;

    public UserRepositoryImpl(Connection conn) {
        this.conn = conn;
    }

    public UserRepositoryImpl() {
    }

    @Override
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Usuario> findAll() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USUARIOS")) {

            while (rs.next()) {
                usuarios.add(crearUsuario(rs));
            }
        }

        return usuarios;
    }

    @Override
    public Usuario findById(Long id) throws SQLException {
        Usuario usuario = null;

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM USUARIOS WHERE id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = crearUsuario(rs);
                }
            }
        }
        return usuario;
    }

    @Override
    public Usuario findByName(String username) throws SQLException {
        Usuario usuario = null;

        try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM usuarios WHERE username = ?")) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = crearUsuario(rs);
                }
            }
        }

        return usuario;
    }

    @Override
    public Usuario save(Usuario usuario) throws SQLException {
        String sql;
        if (usuario.getId() != null && usuario.getId() > 0) {
            sql = "UPDATE USUARIOS SET username = ?, password = ?, email = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO USUARIOS(username, password, email) VALUES(?, ?, ?)";
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getEmail());
            if (usuario.getId() != null && usuario.getId() > 0) {
                stmt.setLong(4, usuario.getId());
            }
            stmt.executeUpdate();

            if (usuario.getId() == null) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        usuario.setId(rs.getLong(1));
                    }
                }
            }
        }

        return usuario;
    }

    @Override
    public void delete(Long id) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM USUARIOS WHERE id = ?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Usuario crearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setEmail(rs.getString("email"));

        return usuario;
    }
}
