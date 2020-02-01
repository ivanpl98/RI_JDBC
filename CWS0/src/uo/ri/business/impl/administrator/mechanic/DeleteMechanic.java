package uo.ri.business.impl.administrator.mechanic;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.WorkOrderDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.Util;
import uo.ri.persistence.MechanicGateway;
import uo.ri.persistence.WorkorderGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DeleteMechanic {

    private Long id;

    public DeleteMechanic(Long id) {
        this.id = id;
    }


    public void execute() throws BusinessException {
        Connection conn = null;
        try {
            conn = Jdbc.getConnection();
            conn.setAutoCommit(false);
            MechanicGateway mg = new MechanicGateway(conn);
            WorkorderGateway wg = new WorkorderGateway(conn);
            if (mg.findById(this.id) == null) {
                conn.rollback();
                throw new BusinessException("The id: " + this.id + " does not exists in the database");
            }
            List<WorkOrderDto> wo = wg.findAll();
            if (wo.stream().anyMatch(x -> x.mechanicId.longValue() == this.id.longValue()))
                throw new BusinessException("The mechanic with id: " + this.id + " cannot be deleted from the database");
            mg.delete(this.id);
            conn.commit();
        } catch (SQLException sqle) {
            Util.rollbackConnection(conn);
            throw new RuntimeException(sqle);
        } finally {
            Jdbc.close(conn);
        }
    }

}
