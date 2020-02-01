package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CashDto;
import uo.ri.common.Conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CashGateway extends AbstractGateway implements Gateway<CashDto> {

    public CashGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(CashDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_ADD_CASH"));

        pst.setLong(1, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_DELETE_CASH"));

        pst.setLong(1, id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    //This method should never be used, as this table only contains the id of
    // the cash, it has no fields to update
    @Override
    public void update(CashDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_UPDATE_CASH"));

        pst.setLong(1, obj.id);
        pst.setLong(2, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<CashDto> findAll() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_CASH"));
        List<CashDto> cs = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return cs;
    }

    private List<CashDto> resultSetToList(ResultSet rs) throws SQLException {
        List<CashDto> cs = new ArrayList<>();
        while (rs.next())
            cs.add(resultSetToCash(rs));
        rs.close();
        return cs;
    }

    private CashDto resultSetToCash(ResultSet rs) throws SQLException {
        CashDto c = new CashDto();
        c.id = rs.getLong("id");
        return c;
    }


}
