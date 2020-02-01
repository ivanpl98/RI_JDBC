package uo.ri.business.impl;

import alb.util.jdbc.Jdbc;
import uo.ri.business.CourseReportService;
import uo.ri.business.dto.CertificateDto;
import uo.ri.business.dto.TrainingForMechanicRow;
import uo.ri.business.dto.TrainingHoursRow;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.persistence.CertificateGateway;
import uo.ri.persistence.MechanicGateway;
import uo.ri.persistence.VehicleTypeGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseReportServiceImpl implements CourseReportService {
    @Override
    public List<TrainingForMechanicRow> findTrainigByMechanicId(Long id) {
        return null;
    }

    @Override
    public List<TrainingHoursRow> findTrainingByVehicleTypeAndMechanic() {
        return null;
    }

    @Override
    public List<CertificateDto> findCertificatedByVehicleType() {
        Connection conn = null;
        List<CertificateDto> cs = null;
        MechanicGateway mg;
        CertificateGateway cg;
        VehicleTypeGateway vtg;
        try {
            conn = Jdbc.getConnection();
            mg = new MechanicGateway(conn);
            cg = new CertificateGateway(conn);
            vtg = new VehicleTypeGateway(conn);
            List<VehicleTypeDto> vts = vtg.findAll();
            cs = new ArrayList<>();
            for (VehicleTypeDto vt : vts) {
                List<CertificateDto> tmp = cg.findByVehicleType(vt.id);
                for (CertificateDto c : tmp) {
                    c.mechanic = mg.findById(c.mechanic.id);
                    c.vehicleType = vt;
                }
                cs.addAll(tmp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Jdbc.close(conn);
        }
        return cs;
    }
}
