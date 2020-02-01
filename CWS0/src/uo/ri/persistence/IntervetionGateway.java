package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.InterventionDto;
import uo.ri.common.Conf;
import uo.ri.common.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IntervetionGateway extends AbstractGateway implements Gateway<InterventionDto> {

    public IntervetionGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(InterventionDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_ADD_INTERVENTION"));

        configurePStatement(pst, obj);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_DELETE_INTERVENTION"));

        pst.setLong(1, id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(InterventionDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_UPDATE_INTERVENTION"));

        configurePStatement(pst, obj);
        pst.setLong(5, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<InterventionDto> findAll() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_INTERVENTIONS"));
        List<InterventionDto> is = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return is;
    }

    public List<InterventionDto> findByWorkorder(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_INTERVENTION_WORKORDER"));
        pst.setLong(1, id);
        List<InterventionDto> is = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return is;
    }


    private void configurePStatement(PreparedStatement pst,
                                     InterventionDto obj) throws SQLException {
        pst.setInt(1, obj.minutes);
        pst.setDate(2, Util.convertToSqlDate(obj.date));
        pst.setLong(3, obj.mechanic_id);
        pst.setLong(4, obj.workorder_id);
    }

    private List<InterventionDto> resultSetToList(ResultSet rs) throws SQLException {
        List<InterventionDto> is = new ArrayList<>();
        while (rs.next())
            is.add(resultSetToIntervention(rs));
        rs.close();
        return is;
    }

    private InterventionDto resultSetToIntervention(ResultSet rs) throws SQLException {
        InterventionDto i = new InterventionDto();
        i.id = rs.getLong("id");
        i.minutes = rs.getInt("minutes");
        i.date = Util.convertFromSqlDate(rs.getDate("date"));
        i.mechanic_id = rs.getLong("mechanic_id");
        i.workorder_id = rs.getLong("workorder_id");
        return i;
    }
}
