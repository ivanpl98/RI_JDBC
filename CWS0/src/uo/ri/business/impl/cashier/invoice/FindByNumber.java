package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.persistence.InvoiceGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class FindByNumber {

    private Connection conn;
    private Long number;

    public FindByNumber(Connection conn, Long number) {
        this.conn = conn;
        this.number = number;
    }

    public InvoiceDto execute() throws SQLException {
        InvoiceGateway ig = new InvoiceGateway(conn);
        return ig.findByNumber(this.number);
    }


}
