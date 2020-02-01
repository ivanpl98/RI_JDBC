package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.WorkOrderDto;
import uo.ri.common.Conf;
import uo.ri.common.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkorderGateway extends AbstractGateway implements Gateway<WorkOrderDto> {

    public WorkorderGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(WorkOrderDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty("SQL_ADD_WORKORDER"));

        setPreparedStatementValues(pst, obj);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty("SQL_DELETE_WORKORDER"));

        pst.setLong(1, id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(WorkOrderDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty("SQL_UPDATE_WORKORDER"));

        setPreparedStatementValues(pst, obj);
        pst.setLong(8, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<WorkOrderDto> findAll() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty("SQL_LIST_WORKORDERS"));
        List<WorkOrderDto> workorders = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return workorders;
    }

    public List<WorkOrderDto> findUnchargedByVehicle(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_WORKORDERS_UNCHARGED_VEHICLE"));
        pst.setLong(1, id);
        List<WorkOrderDto> workorders = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);

        return workorders;
    }

    public List<WorkOrderDto> findByInvoiceId(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_WORKORDERS_INVOICE"));
        pst.setLong(1, id);

        List<WorkOrderDto> workorders = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return workorders;
    }

    public WorkOrderDto findById(Long id) throws SQLException {
        WorkOrderDto wo = null;

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_FIND_WORKORDER_ID"));
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next())
            wo = resultSetToWorkoder(rs);

        Jdbc.close(rs, pst);


        return wo;
    }

    public List<WorkOrderDto> findActive() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty("SQL_LIST_ACTIVE_WORKORDERS"));
        List<WorkOrderDto> workorders = resultSetToList(pst.executeQuery());

        Jdbc.close(pst);


        return workorders;
    }


    private void setPreparedStatementValues(PreparedStatement pst, WorkOrderDto obj) throws SQLException {
        pst.setDouble(1, obj.total);
        pst.setDate(2, Util.convertToSqlDate(obj.date));
        pst.setString(3, obj.description);
        pst.setString(4, obj.status);
        if (obj.invoiceId == 0)
            pst.setNull(5, Types.BIGINT);
        else
            pst.setLong(5, obj.invoiceId);
        if (obj.mechanicId == 0)
            pst.setNull(6, Types.BIGINT);
        else
            pst.setLong(6, obj.mechanicId);
        if (obj.vehicleId == 0)
            pst.setNull(7, Types.BIGINT);
        else
            pst.setLong(7, obj.vehicleId);
    }

    private List<WorkOrderDto> resultSetToList(ResultSet rs) throws SQLException {
        List<WorkOrderDto> workorders = new ArrayList<>();

        while (rs.next())
            workorders.add(resultSetToWorkoder(rs));

        rs.close();

        return workorders;
    }

    private WorkOrderDto resultSetToWorkoder(ResultSet rs) throws SQLException {
        WorkOrderDto work = new WorkOrderDto();
        work.id = rs.getLong("id");
        work.total = rs.getDouble("amount");
        work.date = rs.getDate("date");
        work.description = rs.getString("description");
        work.status = rs.getString("status");
        work.invoiceId = rs.getLong("invoice_id");
        work.mechanicId = rs.getLong("mechanic_id");
        work.vehicleId = rs.getLong("vehicle_id");
        return work;
    }

}
