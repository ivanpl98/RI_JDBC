package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.persistence.InvoiceGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class MarkInvoiceAsPaid {

    private Connection conn;
    private Long id;

    public MarkInvoiceAsPaid(Connection conn, Long id) {
        this.conn = conn;
        this.id = id;
    }

    public void execute() throws SQLException {
        InvoiceGateway ig = new InvoiceGateway(conn);
        InvoiceDto i = ig.findById(this.id);
        i.status = "PAID";
        ig.update(i);
    }
}
