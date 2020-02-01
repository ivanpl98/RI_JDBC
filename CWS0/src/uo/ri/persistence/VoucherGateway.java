package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.VoucherDto;
import uo.ri.common.Conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoucherGateway extends AbstractGateway implements Gateway<VoucherDto> {

    public VoucherGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(VoucherDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_ADD_VOUCHER"));

        pst.setLong(1, obj.id);
        pst.setDouble(2, obj.available);
        pst.setString(3, obj.code);
        pst.setString(4, obj.description);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_DELETE_VOUCHER"));

        pst.setLong(1, id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(VoucherDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_UPDATE_VOUCHER"));

        pst.setDouble(1, obj.available);
        pst.setString(2, obj.code);
        pst.setString(3, obj.description);
        pst.setLong(4, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<VoucherDto> findAll() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_VOUCHERS"));
        List<VoucherDto> vs = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return vs;
    }

    public VoucherDto findById(Long id) throws SQLException {
        VoucherDto v = null;

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_VOUCHER_ID"));
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();

        if (rs.next())
            v = resultSetToVoucher(rs);

        Jdbc.close(rs, pst);


        return v;
    }


    private List<VoucherDto> resultSetToList(ResultSet rs) throws SQLException {
        List<VoucherDto> vs = new ArrayList<>();
        while (rs.next())
            vs.add(resultSetToVoucher(rs));
        rs.close();
        return vs;
    }

    private VoucherDto resultSetToVoucher(ResultSet rs) throws SQLException {
        VoucherDto v = new VoucherDto();
        v.id = rs.getLong("id");
        v.available = rs.getDouble("available");
        v.code = rs.getString("code");
        v.description = rs.getString("description");
        return v;
    }


}
