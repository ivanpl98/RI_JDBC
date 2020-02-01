package uo.ri.common;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Util {

    public static Date convertToSqlDate(java.util.Date date) {
        return new Date(date.getTime());
    }

    public static java.util.Date convertFromSqlDate(Date date) {
        return new java.util.Date(date.getTime());
    }

    public static void rollbackConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
