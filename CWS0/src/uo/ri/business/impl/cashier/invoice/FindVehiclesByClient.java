package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.VehicleDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.persistence.VehicleGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FindVehiclesByClient {

    private Connection conn;
    private Long id;

    public FindVehiclesByClient(Connection conn, Long id) {
        this.conn = conn;
        this.id = id;
    }

    public List<VehicleDto> execute() throws SQLException, BusinessException {
        VehicleGateway vg = new VehicleGateway(conn);
        return vg.findByClient(this.id);
    }
}
