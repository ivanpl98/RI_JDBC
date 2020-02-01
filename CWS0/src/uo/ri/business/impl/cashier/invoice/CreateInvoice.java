package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.persistence.InvoiceGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class CreateInvoice {

    private Connection conn;
    private InvoiceDto i;

    public CreateInvoice(Connection conn, InvoiceDto i) {
        this.conn = conn;
        this.i = i;
    }

    public void execute() throws SQLException {
        InvoiceGateway ig = new InvoiceGateway(conn);
        i.status = "NOT_YET_PAID";
        ig.add(i);
    }

}
