package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.SparePartDto;
import uo.ri.common.Conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SparePartGateway extends AbstractGateway implements Gateway<SparePartDto> {
    public SparePartGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(SparePartDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_ADD_SPAREPART"));

        configurePStatement(pst, obj);

        pst.executeUpdate();

        Jdbc.close(pst);
    }


    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_DELETE_SPAREPART"));

        pst.setLong(1, id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(SparePartDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_UPDATE_SPAREPART"));

        configurePStatement(pst, obj);
        pst.setLong(4, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<SparePartDto> findAll() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_SPAREPARTS"));
        List<SparePartDto> sps = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return sps;
    }

    public SparePartDto findById(Long id) throws SQLException {
        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_SPAREPART_ID"));
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        SparePartDto sp = null;
        if (rs.next())
            sp = resultSetToSparePart(rs);

        Jdbc.close(rs, pst);

        return sp;
    }

    private void configurePStatement(PreparedStatement pst, SparePartDto obj) throws SQLException {
        pst.setString(1, obj.code);
        pst.setString(2, obj.description);
        pst.setDouble(3, obj.price);
    }

    private List<SparePartDto> resultSetToList(ResultSet rs) throws SQLException {
        List<SparePartDto> sps = new ArrayList<>();
        while (rs.next())
            sps.add(resultSetToSparePart(rs));
        rs.close();
        return sps;
    }

    private SparePartDto resultSetToSparePart(ResultSet rs) throws SQLException {
        SparePartDto sp = new SparePartDto();
        sp.id = rs.getLong("id");
        sp.code = rs.getString("code");
        sp.description = rs.getString("description");
        sp.price = rs.getDouble("price");
        return sp;
    }

}
