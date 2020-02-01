package uo.ri.business.impl.administrator.course;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.persistence.CourseGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class FindById {

    private Long id;

    public FindById(Long id) {
        this.id = id;
    }

    public CourseDto execute() {
        Connection conn = null;
        CourseGateway cg;
        CourseDto c = null;
        try {
            conn = Jdbc.getConnection();
            cg = new CourseGateway(conn);
            c = cg.findById(this.id);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Jdbc.close(conn);
        }
        return c;
    }
}
