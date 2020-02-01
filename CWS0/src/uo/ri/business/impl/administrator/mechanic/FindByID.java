package uo.ri.business.impl.administrator.mechanic;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.persistence.MechanicGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class FindByID {

    private Long id;

    public FindByID(Long id) {
        this.id = id;
    }

    public MechanicDto excecute() throws BusinessException {
        Connection conn = null;
        try {
            conn = Jdbc.getConnection();
            MechanicGateway mg = new MechanicGateway(conn);
            MechanicDto m = mg.findById(this.id);
            if (m == null)
                throw new BusinessException("The id provided (" + this.id +
                        ") doesn't belong to any mechanic in the database.");
            return m;
        } catch (SQLException sqle) {
            throw new RuntimeException(sqle);
        } finally {
            Jdbc.close(conn);
        }
    }

}
