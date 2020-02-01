package uo.ri.business.impl.administrator.course;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.DedicationDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.Util;
import uo.ri.persistence.CourseGateway;
import uo.ri.persistence.DedicationGateway;
import uo.ri.persistence.EnrollmentGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DeleteCourse {

    private Long id;

    public DeleteCourse(Long id) {
        this.id = id;
    }

    public void execute() throws BusinessException {
        Connection conn = null;
        CourseGateway cg;
        try {
            conn = Jdbc.getConnection();
            cg = new CourseGateway(conn);
            conn.setAutoCommit(false);
            makeValidationsConnection(conn, cg);
            deleteDedications(conn);
            cg.delete(this.id);
            conn.commit();
        } catch (SQLException sqle) {
            Util.rollbackConnection(conn);
            throw new RuntimeException(sqle);
        } finally {
            Jdbc.close(conn);
        }

    }

    private void makeValidationsConnection(Connection conn,
                                           CourseGateway cg) throws SQLException, BusinessException {
        EnrollmentGateway eg = new EnrollmentGateway(conn);
        if (cg.findById(this.id) == null)
            throw new BusinessException("The course with the provided id(" + this.id + ") does not exist.");
        if (eg.findByCourse(this.id).size() > 0)
            throw new BusinessException("The course with the provided id(" +
                    this.id + ") cannot be deletes as it has enrollments associated with it.");
    }

    private void deleteDedications(Connection conn) throws SQLException {
        DedicationGateway dg = new DedicationGateway(conn);

        List<DedicationDto> ds = dg.findByCourse(this.id);

        for (DedicationDto d : ds)
            dg.delete(d.id);

    }
}
