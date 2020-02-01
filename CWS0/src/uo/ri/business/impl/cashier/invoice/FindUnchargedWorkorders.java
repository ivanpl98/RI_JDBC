package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.WorkOrderDto;
import uo.ri.persistence.WorkorderGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FindUnchargedWorkorders {

    private Connection conn;
    private Long id;

    public FindUnchargedWorkorders(Connection conn, Long id) {
        this.conn = conn;
        this.id = id;
    }

    public List<WorkOrderDto> execute() throws SQLException {
        WorkorderGateway wg = new WorkorderGateway(conn);
        return wg.findUnchargedByVehicle(this.id);
    }

}
