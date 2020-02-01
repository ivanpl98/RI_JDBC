package uo.ri.business.impl.administrator.mechanic;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.persistence.MechanicGateway;
import uo.ri.persistence.WorkorderGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

class FindActive {


    public List<MechanicDto> execute() {
        Connection conn = null;
        try {
            conn = Jdbc.getConnection();
            MechanicGateway mg = new MechanicGateway(conn);
            WorkorderGateway wg = new WorkorderGateway(conn);
            List<Long> work =
                    wg.findActive().stream().map(x -> x.mechanicId).collect(Collectors.toList());
            return mg.findAll().stream().filter(x -> work.contains(x.id)).collect(Collectors.toList());
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        } finally {
            Jdbc.close(conn);
        }
    }
}
