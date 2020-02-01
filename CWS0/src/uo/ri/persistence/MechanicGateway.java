package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.MechanicDto;
import uo.ri.common.Conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MechanicGateway extends AbstractGateway implements Gateway<MechanicDto> {


    public MechanicGateway(Connection conn) {
        super(conn);
    }


    @Override
    public void add(MechanicDto mechanic) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty("SQL_ADD_MECHANIC"));

        configurePStatement(pst, mechanic);

        pst.executeUpdate();

        Jdbc.close(pst);

    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty("SQL_DELETE_MECHANIC"));

        pst.setLong(1, id);


        pst.executeUpdate();

        Jdbc.close(pst);

    }

    @Override
    public void update(MechanicDto mechanic) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty("SQL_UPDATE_MECHANIC"));


        configurePStatement(pst, mechanic);

        pst.setLong(4, mechanic.id);

        pst.executeUpdate();

        Jdbc.close(pst);

    }

    @Override
    public List<MechanicDto> findAll() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty("SQL_LIST_MECHANICS"));
        List<MechanicDto> mechanics = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return mechanics;
    }

    public MechanicDto findById(Long id) throws SQLException {
        MechanicDto mechanic = null;

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty("SQL_FIND_MECHANIC_ID"));
        pst.setLong(1, id);

        ResultSet rs = pst.executeQuery();

        if (rs.next())
            mechanic = resultSetToMechanic(rs);


        Jdbc.close(rs, pst);

        return mechanic;

    }

    public MechanicDto findByDni(String dni) throws SQLException {
        MechanicDto mechanic = null;

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty("SQL_FIND_MECHANIC_DNI"));

        pst.setString(1, dni);

        ResultSet rs = pst.executeQuery();

        if (rs.next())
            mechanic = resultSetToMechanic(rs);


        Jdbc.close(rs, pst);

        return mechanic;

    }

    private void configurePStatement(PreparedStatement pst, MechanicDto obj) throws SQLException {
        pst.setString(1, obj.dni);
        pst.setString(2, obj.name);
        pst.setString(3, obj.surname);
    }

    private List<MechanicDto> resultSetToList(ResultSet rs) throws SQLException {
        List<MechanicDto> mechanics = new ArrayList<>();

        while (rs.next())
            mechanics.add(resultSetToMechanic(rs));
        rs.close();

        return mechanics;
    }

    private MechanicDto resultSetToMechanic(ResultSet rs) throws SQLException {
        MechanicDto mec = new MechanicDto();
        mec.id = rs.getLong("id");
        mec.dni = rs.getString("dni");
        mec.name = rs.getString("name");
        mec.surname = rs.getString("surname");
        return mec;
    }
}
