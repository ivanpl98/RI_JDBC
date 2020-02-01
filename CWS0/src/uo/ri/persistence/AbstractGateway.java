package uo.ri.persistence;

import java.sql.Connection;

class AbstractGateway {

    Connection conn;

    AbstractGateway() {

    }

    AbstractGateway(Connection conn) {
        this.conn = conn;
    }

    public void setConnection(Connection conn) {
        this.conn = conn;
    }


}
