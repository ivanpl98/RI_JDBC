package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.VehicleDto;
import uo.ri.common.Conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleGateway extends AbstractGateway implements Gateway<VehicleDto> {

    public VehicleGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(VehicleDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_ADD_VEHICLE"));

        configurePStatement(pst, obj);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_DELETE_VEHICLE"));

        pst.setLong(1, id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(VehicleDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_UPDATE_VEHICLE"));

        configurePStatement(pst, obj);
        pst.setLong(6, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);

    }

    @Override
    public List<VehicleDto> findAll() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_VEHICLES"));
        List<VehicleDto> vs = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);

        return vs;
    }

    public List<VehicleDto> findByClient(Long id) throws SQLException {
        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_VEHICLES_CLIENT"));
        pst.setLong(1, id);
        List<VehicleDto> vs = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);

        return vs;
    }

    public VehicleDto findById(Long id) throws SQLException {
        VehicleDto vs = null;

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_VEHICLE_ID"));
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();

        if (rs.next())
            vs = resultSetToVehicle(rs);

        Jdbc.close(rs, pst);

        return vs;
    }

    private void configurePStatement(PreparedStatement pst,
                                     VehicleDto obj) throws SQLException {
        pst.setString(1, obj.plate);
        pst.setString(2, obj.make);
        pst.setString(3, obj.model);
        pst.setLong(4, obj.clientId);
        pst.setLong(5, obj.vehicleTypeId);
    }

    private List<VehicleDto> resultSetToList(ResultSet rs) throws SQLException {
        List<VehicleDto> vs = new ArrayList<>();
        while (rs.next())
            vs.add(resultSetToVehicle(rs));
        rs.close();
        return vs;
    }

    private VehicleDto resultSetToVehicle(ResultSet rs) throws SQLException {
        VehicleDto v = new VehicleDto();

        v.id = rs.getLong("id");
        v.plate = rs.getString("plateNumber");
        v.make = rs.getString("make");
        v.model = rs.getString("model");
        v.clientId = rs.getLong("client_id");
        v.vehicleTypeId = rs.getLong("vehicleType_id");

        return v;
    }
}
