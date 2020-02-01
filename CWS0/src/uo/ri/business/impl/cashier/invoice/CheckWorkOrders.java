package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.WorkOrderDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.persistence.WorkorderGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CheckWorkOrders {

    private Connection conn;
    private List<Long> wos;

    public CheckWorkOrders(Connection conn, List<Long> wos) {
        this.conn = conn;
        this.wos = wos;
    }

    public void execute() throws BusinessException, SQLException {
        WorkorderGateway wg = new WorkorderGateway(conn);
        for (Long id : wos) {
            WorkOrderDto wo = wg.findById(id);

            if (wo == null) {
                throw new BusinessException("Workorder " + id + " doesn't" +
                        " exist");
            }
            if (!"FINISHED".equalsIgnoreCase(wo.status)) {
                throw new BusinessException("Workorder " + id + " is not " +
                        "finished yet");
            }
        }
    }
}
