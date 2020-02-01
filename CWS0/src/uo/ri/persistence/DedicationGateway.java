package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.business.dto.DedicationDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.common.Conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DedicationGateway extends AbstractGateway implements Gateway<DedicationDto> {

    public DedicationGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(DedicationDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_ADD_DEDICATION"));

        configurePStatement(pst, obj);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_DELETE_DEDICATION"));

        pst.setLong(1, id);


        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(DedicationDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_UPDATE_DEDICATION"));

        configurePStatement(pst, obj);
        pst.setLong(4, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<DedicationDto> findAll() throws SQLException {
        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_DEDICATIONS"));
        List<DedicationDto> ds = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);

        return ds;
    }

    public List<DedicationDto> findByCourse(Long courseId) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_DEDICATION_COURSE_VEHICLETYPE"));
        pst.setLong(1, courseId);

        List<DedicationDto> ds = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);

        return ds;

    }

    private void configurePStatement(PreparedStatement pst, DedicationDto obj) throws SQLException {
        pst.setInt(1, obj.percentage);
        pst.setLong(2, obj.course.id);
        pst.setLong(3, obj.vehicleType.id);
    }

    private List<DedicationDto> resultSetToList(ResultSet rs) throws SQLException {
        List<DedicationDto> ds = new ArrayList<>();
        while (rs.next())
            ds.add(resultSetToDedication(rs));
        rs.close();
        return ds;
    }

    private DedicationDto resultSetToDedication(ResultSet rs) throws SQLException {
        DedicationDto d = new DedicationDto();
        d.id = rs.getLong("id");
        d.percentage = rs.getInt("percentage");
        d.course = new CourseDto();
        d.course.id = rs.getLong("course_id");
        d.vehicleType = new VehicleTypeDto();
        d.vehicleType.id = rs.getLong("vehicletype_id");
        return d;
    }


}
