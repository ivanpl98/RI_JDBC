package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.EnrollmentDto;
import uo.ri.common.Conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentGateway extends AbstractGateway implements Gateway<EnrollmentDto> {

    public EnrollmentGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(EnrollmentDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_ADD_ENROLLMENT"));

        configurePStatement(pst, obj);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_DELETE_ENROLLMENT"));

        pst.setLong(1, id);


        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(EnrollmentDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_UPDATE_ENROLLMENT"));


        configurePStatement(pst, obj);

        pst.setLong(5, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<EnrollmentDto> findAll() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_ENROLLMENTS"));
        List<EnrollmentDto> ers = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return ers;
    }


    public List<EnrollmentDto> findByMechanic(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_ENROLLMENTS_MECHANIC"));

        pst.setLong(1, id);
        List<EnrollmentDto> ers = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);

        return ers;
    }

    public List<EnrollmentDto> findByCourse(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_ENROLLMENTS_COURSE"));

        pst.setLong(1, id);
        List<EnrollmentDto> ers = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);

        return ers;
    }

    private void configurePStatement(PreparedStatement pst,
                                     EnrollmentDto obj) throws SQLException {
        pst.setInt(1, obj.attendance);
        pst.setBoolean(2, obj.passed);
        pst.setLong(3, Long.parseLong(obj.courseId));
        pst.setLong(4, Long.parseLong(obj.mechanicId));
    }

    private List<EnrollmentDto> resultSetToList(ResultSet rs) throws SQLException {
        List<EnrollmentDto> ers = new ArrayList<>();
        while (rs.next())
            ers.add(resultSetToDedication(rs));
        rs.close();
        return ers;
    }

    private EnrollmentDto resultSetToDedication(ResultSet rs) throws SQLException {
        EnrollmentDto er = new EnrollmentDto();
        er.id = rs.getLong("id");
        er.attendance = rs.getInt("attendance");
        er.passed = rs.getBoolean("passed");
        er.mechanicId = String.valueOf(rs.getLong("mechanic_id"));
        er.courseId = String.valueOf(rs.getLong("course_id"));
        return er;
    }


}
