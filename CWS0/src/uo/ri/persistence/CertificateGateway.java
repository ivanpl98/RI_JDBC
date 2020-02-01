package uo.ri.persistence;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CertificateDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.common.Conf;
import uo.ri.common.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CertificateGateway extends AbstractGateway implements Gateway<CertificateDto> {

    public CertificateGateway(Connection conn) {
        super(conn);
    }

    @Override
    public void add(CertificateDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_ADD_CERTIFICATE"));

        configurePStatement(pst, obj);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_DELETE_CERTIFICATE"));

        pst.setLong(1, id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public void update(CertificateDto obj) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_UPDATE_CERTIFICATE"));

        configurePStatement(pst, obj);
        pst.setLong(4, obj.id);

        pst.executeUpdate();

        Jdbc.close(pst);
    }

    @Override
    public List<CertificateDto> findAll() throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_CERTIFICATES"));

        List<CertificateDto> cfs = reesultSetToList(pst.executeQuery());

        Jdbc.close(pst);

        return cfs;
    }

    public List<CertificateDto> findByVehicleType(Long id) throws SQLException {

        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_LIST_CERTIFICATES_VEHICLE_TYPE"));
        pst.setLong(1, id);

        List<CertificateDto> cfs = reesultSetToList(pst.executeQuery());

        Jdbc.close(pst);

        return cfs;
    }

    public boolean checkExistance(CertificateDto cd) throws SQLException {
        PreparedStatement pst = this.conn.prepareStatement(Conf.getInstance().getProperty(
                "SQL_CHECK_CERTIFICATE_EXISTANCE"));
        pst.setLong(1, cd.mechanic.id);
        pst.setLong(2, cd.vehicleType.id);

        ResultSet rs = pst.executeQuery();

        if (rs.next())
            return true;

        Jdbc.close(rs, pst);

        return false;
    }

    private void configurePStatement(PreparedStatement pst,
                                     CertificateDto obj) throws SQLException {
        pst.setDate(1, Util.convertToSqlDate(obj.obtainedAt));
        pst.setLong(2, obj.mechanic.id);
        pst.setLong(3, obj.vehicleType.id);
    }

    private List<CertificateDto> reesultSetToList(ResultSet rs) throws SQLException {
        List<CertificateDto> cfs = new ArrayList<>();
        while (rs.next())
            cfs.add(resultSetToCertificate(rs));
        rs.close();
        return cfs;
    }

    private CertificateDto resultSetToCertificate(ResultSet rs) throws SQLException {
        CertificateDto cf = new CertificateDto();
        cf.id = rs.getLong("id");
        cf.obtainedAt = Util.convertFromSqlDate(rs.getDate("date"));
        cf.mechanic = new MechanicDto();
        cf.mechanic.id = rs.getLong("mechanic_id");
        cf.vehicleType = new VehicleTypeDto();
        cf.vehicleType.id = rs.getLong("vehicleType_id");
        return cf;
    }


}
