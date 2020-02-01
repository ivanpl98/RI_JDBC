package uo.ri.business.impl;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import uo.ri.business.CertificateService;
import uo.ri.business.dto.*;
import uo.ri.persistence.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CertificateServiceImpl implements CertificateService {
    @Override
    public int generateCertificates() {
        int gc = 0;
        Connection conn = null;
        try {
            conn = Jdbc.getConnection();
            conn.setAutoCommit(false);

            //Create the different gateways involved
            MechanicGateway mg = new MechanicGateway(conn);

            //Retrieve all the information relevant for generating the
            // certificates
            List<MechanicDto> ms = mg.findAll();

            //For each mechanic
            for (MechanicDto m : ms) {
                gc += generateForMechanic(conn, m);
            }

            conn.commit();
        } catch (SQLException sqle) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(sqle);
        }
        return gc;
    }

    private int generateForMechanic(Connection conn, MechanicDto m) throws SQLException {
        int gc = 0;
        VehicleTypeGateway vtg = new VehicleTypeGateway(conn);

        List<VehicleTypeDto> vts = vtg.findAll();

        for (VehicleTypeDto vt : vts) {
            gc += generateForMechanicAndVehicleType(conn, m, vt);
        }
        return gc;
    }

    private int generateForMechanicAndVehicleType(Connection conn, MechanicDto m, VehicleTypeDto vt) throws SQLException {
        int gc = 0;
        //Hours dedicated to each vehicleType
        int hours = 0;

        EnrollmentGateway eg = new EnrollmentGateway(conn);

        //Find Mechanic enrollments
        List<EnrollmentDto> es = eg.findByMechanic(m.id);

        for (EnrollmentDto e : es) {
            hours += calculateDedicatedHours(conn, e, vt);

        }
        if (hours > vt.minTrainigHours) {
            gc += createCertificate(conn, m, vt);

        }

        return gc;
    }

    private int createCertificate(Connection conn, MechanicDto m,
                                  VehicleTypeDto vt) throws SQLException {
        CertificateGateway cfg = new CertificateGateway(conn);
        CertificateDto cd = new CertificateDto();
        cd.mechanic = m;
        cd.vehicleType = vt;
        cd.obtainedAt = Dates.today();
        if (!cfg.checkExistance(cd)) {
            cfg.add(cd);
            return 1;
        }
        return 0;
    }

    private int calculateDedicatedHours(Connection conn, EnrollmentDto e, VehicleTypeDto vt) throws SQLException {
        CourseGateway cg = new CourseGateway(conn);
        DedicationGateway dg = new DedicationGateway(conn);

        int hours = 0;

        if (e.passed) {
            CourseDto c = cg.findById(Long.parseLong(e.courseId));
            List<DedicationDto> ds =
                    dg.findByCourse(Long.parseLong(e.courseId));
            Optional<DedicationDto> d =
                    ds.stream().filter(x -> x.vehicleType.id.equals(vt.id)).findAny();
            if (d.isPresent()) {
                DedicationDto dto = d.get();
                int percentage = dto.percentage;
                hours += Double.valueOf(c.hours) * (Double.valueOf(percentage) / 100.0) * (Double.valueOf(e.attendance) / 100.0);
            }
        }
        return hours;
    }
}
