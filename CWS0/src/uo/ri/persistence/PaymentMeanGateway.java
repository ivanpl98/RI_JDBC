package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.CashDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.common.Conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentMeanGateway extends AbstractGateway implements Gateway<PaymentMeanDto> {
    public PaymentMeanGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(PaymentMeanDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_ADD_PAYMENTMEAN"));

        pst.setLong(1, obj.id);
        if (obj instanceof CashDto)
            pst.setString(2, "TCashes");
        else if (obj instanceof CardDto)
            pst.setString(2, "TCreditCards");
        else
            pst.setString(2, "TVouchers");

        pst.setDouble(3, obj.accumulated);
        pst.setLong(4, obj.clientId);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_DELETE_PAYMENTMEAN"));

        pst.setLong(1, id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(PaymentMeanDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_UPDATE_PAYMENTMEAN"));

        if (obj instanceof CashDto)
            pst.setString(1, "TCashes");
        else if (obj instanceof CardDto)
            pst.setString(1, "TCreditCards");
        else
            pst.setString(1, "TVouchers");

        pst.setDouble(2, obj.accumulated);
        pst.setLong(3, obj.clientId);
        pst.setLong(4, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<PaymentMeanDto> findAll() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_PAYMENTMEANS"));
        List<PaymentMeanDto> ps = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return ps;
    }

    public List<PaymentMeanDto> findByUser(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_PAYMENTMEANS_USER"));
        pst.setLong(1, id);
        List<PaymentMeanDto> ps = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return ps;
    }

    public PaymentMeanDto findById(Long id) throws SQLException {
        PaymentMeanDto p = null;
        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_PAYMENTMEANS_ID"));
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next())
            p = resultSetToPaymentMean(rs);

        Jdbc.close(rs, pst);
        return p;
    }

    private List<PaymentMeanDto> resultSetToList(ResultSet rs) throws SQLException {
        List<PaymentMeanDto> ps = new ArrayList<>();
        while (rs.next())
            ps.add(resultSetToPaymentMean(rs));
        rs.close();
        return ps;
    }

    private PaymentMeanDto resultSetToPaymentMean(ResultSet rs) throws SQLException {
        PaymentMeanDto p = null;
        String pType = rs.getString("dtype");
        switch (pType) {
            case "TCashes":
                p = new CashDto();
                break;
            case "TCreditCards":
                p = new CardDto();
                break;
            case "TVouchers":
                p = new VoucherDto();
                break;
        }
        p.id = rs.getLong("id");
        p.clientId = rs.getLong("client_id");
        p.accumulated = rs.getDouble("accumulated");
        return p;
    }

}
