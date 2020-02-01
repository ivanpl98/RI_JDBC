package uo.ri.business.impl.administrator.course;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.persistence.CourseGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FindAll {

    public List<CourseDto> execute() {
        Connection conn = null;
        CourseGateway cg;
        List<CourseDto> cs = null;
        try {
            conn = Jdbc.getConnection();
            cg = new CourseGateway(conn);
            cs = cg.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Jdbc.close(conn);
        }
        return cs;
    }
}
