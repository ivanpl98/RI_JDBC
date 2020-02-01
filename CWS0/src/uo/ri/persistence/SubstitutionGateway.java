package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.SubstitutionDto;
import uo.ri.common.Conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubstitutionGateway extends AbstractGateway implements Gateway<SubstitutionDto> {
    public SubstitutionGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(SubstitutionDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_ADD_SUBSTITUTION"));

        configurePStatement(pst, obj);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_DELETE_SUBSTITUTION"));

        pst.setLong(1, id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(SubstitutionDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_UPDATE_SUBSTITUTION"));

        configurePStatement(pst, obj);
        pst.setLong(4, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<SubstitutionDto> findAll() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_SUBSTITUTIONS"));
        List<SubstitutionDto> sbs = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return sbs;
    }

    public List<SubstitutionDto> findByIntervention(Long id) throws SQLException {
        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_SUBSTITUTION_INTERVENTION"));
        pst.setLong(1, id);
        List<SubstitutionDto> sbs = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return sbs;
    }

    private void configurePStatement(PreparedStatement pst,
                                     SubstitutionDto obj) throws SQLException {
        pst.setInt(1, obj.quantity);
        pst.setLong(2, obj.intervention_id);
        pst.setLong(3, obj.sparepart_id);
    }

    private List<SubstitutionDto> resultSetToList(ResultSet rs) throws SQLException {
        List<SubstitutionDto> sps = new ArrayList<>();
        while (rs.next())
            sps.add(resultSetToSubstitution(rs));
        rs.close();
        return sps;
    }

    private SubstitutionDto resultSetToSubstitution(ResultSet rs) throws SQLException {
        SubstitutionDto sb = new SubstitutionDto();
        sb.id = rs.getLong("id");
        sb.quantity = rs.getInt("quantity");
        sb.intervention_id = rs.getLong("intervention_id");
        sb.sparepart_id = rs.getLong("sparepart_id");
        return sb;
    }


}
