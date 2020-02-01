package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ClientDto;
import uo.ri.common.Conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientGateway extends AbstractGateway implements Gateway<ClientDto> {


    public ClientGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(ClientDto obj) throws SQLException {

        PreparedStatement pst =
                this.conn.prepareStatement(Conf.getInstance().getProperty(
                        "SQL_ADD_CLIENT"));

        configurePStatement(pst, obj);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {
        PreparedStatement pst =
                this.conn.prepareStatement(Conf.getInstance().getProperty(
                        "SQL_DELETE_CLIENT"));

        pst.setLong(1, id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(ClientDto obj) throws SQLException {
        PreparedStatement pst =
                this.conn.prepareStatement(Conf.getInstance().getProperty(
                        "SQL_UPDATE_CLIENT"));

        configurePStatement(pst, obj);
        pst.setLong(9, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<ClientDto> findAll() throws SQLException {
        PreparedStatement pst =
                this.conn.prepareStatement(Conf.getInstance().getProperty(
                        "SQL_LIST_CLIENTS"));
        List<ClientDto> cs = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return cs;
    }

    public ClientDto findByDni(String dni) throws SQLException {
        ClientDto c = null;

        PreparedStatement pst =
                this.conn.prepareStatement(Conf.getInstance().getProperty(
                        "SQL_FIND_CLIENT_DNI"));

        pst.setString(1, dni);

        ResultSet rs = pst.executeQuery();

        if (rs.next())
            c = resultSetToClient(rs);


        Jdbc.close(rs, pst);

        return c;

    }

    private void configurePStatement(PreparedStatement pst, ClientDto obj) throws SQLException {
        pst.setString(1, obj.dni);
        pst.setString(2, obj.email);
        pst.setString(3, obj.name);
        pst.setString(4, obj.phone);
        pst.setString(5, obj.surname);
        pst.setString(6, obj.addressCity);
        pst.setString(7, obj.addressStreet);
        pst.setString(8, obj.addressZipcode);
    }

    private List<ClientDto> resultSetToList(ResultSet rs) throws SQLException {
        List<ClientDto> cs = new ArrayList<>();

        while (rs.next())
            cs.add(resultSetToClient(rs));
        rs.close();

        return cs;
    }

    private ClientDto resultSetToClient(ResultSet rs) throws SQLException {
        ClientDto c = new ClientDto();
        c.id = rs.getLong("id");
        c.dni = rs.getString("dni");
        c.name = rs.getString("name");
        c.surname = rs.getString("surname");
        c.email = rs.getString("email");
        c.phone = rs.getString("phone");
        c.addressCity = rs.getString("city");
        c.addressStreet = rs.getString("street");
        c.addressZipcode = rs.getString("zipcode");
        return c;
    }
}
