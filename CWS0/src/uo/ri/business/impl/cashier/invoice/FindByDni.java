package uo.ri.business.impl.cashier.invoice;

import uo.ri.business.dto.ClientDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.persistence.ClientGateway;

import java.sql.Connection;
import java.sql.SQLException;

public class FindByDni {

    private Connection conn;
    private String dni;

    public FindByDni(Connection conn, String dni) {
        this.conn = conn;
        this.dni = dni;
    }

    public ClientDto execute() throws SQLException, BusinessException {
        ClientGateway cg = new ClientGateway(conn);
        ClientDto c = cg.findByDni(this.dni);
        if (c == null)
            throw new BusinessException("The dni introduced(" + this.dni + ") " +
                    "doesn't exist in the database");
        return c;
    }
}
