package uo.ri.business.impl.cashier.invoice;

import uo.ri.persistence.InvoiceGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class GenerateInvoiceNumber {

    private Connection conn;

    public GenerateInvoiceNumber(Connection conn) {
        this.conn = conn;
    }

    public Long execute() throws SQLException {
        InvoiceGateway ig = new InvoiceGateway(conn);
        return ig.generateNewInvoiceNumber();
    }
}
