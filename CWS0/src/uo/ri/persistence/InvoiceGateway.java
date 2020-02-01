package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.common.Conf;
import uo.ri.common.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceGateway extends AbstractGateway implements Gateway<InvoiceDto> {

    public InvoiceGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(InvoiceDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_ADD_INVOICE"));

        configurePStatement(pst, obj);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_DELETE_INVOICE"));

        pst.setLong(1, id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(InvoiceDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_UPDATE_INVOICE"));

        configurePStatement(pst, obj);
        pst.setLong(6, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<InvoiceDto> findAll() throws SQLException {
        List<InvoiceDto> is;

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_INVOICES"));

        is = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);

        return is;
    }

    public InvoiceDto findById(Long idFactura) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_INVOICE_ID"));
        return getInvoiceDto(idFactura, pst);
    }


    public InvoiceDto findByNumber(Long number) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_INVOICE_NUMBER"));
        return getInvoiceDto(number, pst);
    }

    private InvoiceDto getInvoiceDto(Long idFactura, PreparedStatement pst) throws SQLException {
        pst.setLong(1, idFactura);
        ResultSet rs = pst.executeQuery();
        InvoiceDto i = null;

        if (rs.next())
            i = resultSetToInvoice(rs);

        Jdbc.close(rs, pst);

        return i;
    }

    public Long generateNewInvoiceNumber() throws SQLException {
        Long last = 0L;

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_GENERATE_INVOICE_NUMBER"));

        ResultSet rs = pst.executeQuery();

        if (rs.next())
            last = rs.getLong(1);

        Jdbc.close(pst);

        return last + 1;
    }

    private void configurePStatement(PreparedStatement pst,
                                     InvoiceDto invoice) throws SQLException {
        pst.setLong(1, invoice.number);
        pst.setDate(2, Util.convertToSqlDate(invoice.date));
        pst.setString(3, invoice.status);
        pst.setDouble(4, invoice.total);
        pst.setDouble(5, invoice.vat);
    }

    private List<InvoiceDto> resultSetToList(ResultSet rs) throws SQLException {
        List<InvoiceDto> is = new ArrayList<>();

        while (rs.next())
            is.add(resultSetToInvoice(rs));
        rs.close();

        return is;
    }

    private InvoiceDto resultSetToInvoice(ResultSet rs) throws SQLException {
        InvoiceDto i = new InvoiceDto();
        i.id = rs.getLong("id");
        i.number = rs.getLong("number");
        i.date = Util.convertFromSqlDate(rs.getDate("date"));
        i.status = rs.getString("status");
        i.total = rs.getDouble("amount");
        i.vat = rs.getDouble("vat");
        return i;
    }


}
