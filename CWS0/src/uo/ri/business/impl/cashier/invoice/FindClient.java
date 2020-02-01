package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.WorkOrderDto;
import uo.ri.persistence.VehicleGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class FindClient {

    private Connection conn;
    private WorkOrderDto wo;

    public FindClient(Connection conn, WorkOrderDto wo) {
        this.conn = conn;
        this.wo = wo;
    }

    public Long execute() throws SQLException {
        VehicleGateway vg = new VehicleGateway(conn);
        return vg.findById(wo.vehicleId).clientId;
    }
}
