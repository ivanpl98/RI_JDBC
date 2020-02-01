package uo.ri.business.impl.administrator.course;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.persistence.VehicleTypeGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FindVehicleTypes {

    public List<VehicleTypeDto> execute() {
        Connection conn = null;
        VehicleTypeGateway vtg;
        List<VehicleTypeDto> vts = null;
        try {
            conn = Jdbc.getConnection();
            vtg = new VehicleTypeGateway(conn);
            vts = vtg.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Jdbc.close(conn);
        }
        return vts;
    }
}
