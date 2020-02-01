package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.WorkOrderDto;
import uo.ri.persistence.WorkorderGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class LinkWorkorderInvoice {

    private Connection conn;
    private List<Long> wos;
    private Long i;

    public LinkWorkorderInvoice(Connection conn, List<Long> wos, Long i) {
        this.conn = conn;
        this.wos = wos;
        this.i = i;
    }

    public void execute() throws SQLException {
        WorkorderGateway wg = new WorkorderGateway(conn);
        for (Long wo : wos) {
            WorkOrderDto w = wg.findById(wo);
            w.invoiceId = this.i;
            wg.update(w);
        }
    }

}
