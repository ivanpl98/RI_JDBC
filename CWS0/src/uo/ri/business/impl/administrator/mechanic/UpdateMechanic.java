package uo.ri.business.impl.administrator.mechanic;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.Util;
import uo.ri.persistence.MechanicGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class UpdateMechanic {

    private MechanicDto mechanic;

    public UpdateMechanic(MechanicDto mechanic) {
        this.mechanic = mechanic;
    }

    public void execute() throws BusinessException {
        Connection conn = null;
        try {
            conn = Jdbc.getConnection();
            conn.setAutoCommit(false);
            MechanicGateway mg = new MechanicGateway(conn);
            if (mg.findById(this.mechanic.id) == null)
                throw new BusinessException("The id: " + this.mechanic.id + " does not exists in the database");
            mg.update(this.mechanic);
            conn.commit();
        } catch (SQLException sqle) {
            Util.rollbackConnection(conn);
            throw new RuntimeException(sqle);
        } finally {
            Jdbc.close(conn);
        }
    }
}
