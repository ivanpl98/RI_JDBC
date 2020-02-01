package uo.ri.persistence;

import java.sql.SQLException;
import java.util.List;

interface Gateway<T> {

    void add(T obj) throws SQLException;

    void delete(Long id) throws SQLException;

    void update(T obj) throws SQLException;

    List<T> findAll() throws SQLException;
}
