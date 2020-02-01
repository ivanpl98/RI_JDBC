package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.WorkOrderDto;
import uo.ri.persistence.WorkorderGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MarkWorkorderInvoiced {

    private List<Long> wos;
    private Connection conn;

    public MarkWorkorderInvoiced(Connection conn, List<Long> wos) {
        this.conn = conn;
        this.wos = wos;
    }

    public void execute() throws SQLException {
        WorkorderGateway wg = new WorkorderGateway(conn);
        for (Long wo : wos) {
            WorkOrderDto w = wg.findById(wo);
            w.status = "INVOICED";
            wg.update(w);
        }
    }
}
