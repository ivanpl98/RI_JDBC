package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.persistence.InvoiceGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class FindById {

    private Connection conn;
    private Long idFactura;

    public FindById(Connection conn, Long idFactura) {
        this.conn = conn;
        this.idFactura = idFactura;
    }

    public InvoiceDto execute() throws SQLException {
        InvoiceGateway ig = new InvoiceGateway(conn);
        return ig.findById(this.idFactura);
    }
}
