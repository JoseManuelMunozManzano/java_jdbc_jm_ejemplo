package com.jmunoz.bbdd.comics.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {

    void setConn(Connection conn);

    List<T> findAll() throws SQLException;

    T findByName(String name) throws SQLException;

    T findById(Long id) throws SQLException;

    T save(T t) throws SQLException;

    void delete(Long id) throws SQLException;
}
