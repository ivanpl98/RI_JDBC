package uo.ri.business.impl.administrator.course;

import alb.util.date.Dates;
import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.CourseDto;
import uo.ri.business.dto.DedicationDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.Util;
import uo.ri.persistence.CourseGateway;
import uo.ri.persistence.DedicationGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UpdateCourse {

    private CourseDto c;

    public UpdateCourse(CourseDto c) {
        this.c = c;
    }

    public void execute() throws BusinessException {
        AddCourse.makeCourseValidationsNoConnection(this.c);
        Connection conn = null;
        CourseGateway cg;
        try {
            conn = Jdbc.getConnection();
            conn.setAutoCommit(false);
            cg = new CourseGateway(conn);
            makeCourseValidationsConnection(cg);
            cg.update(c);
            updateDedications(conn);
            conn.commit();
        } catch (SQLException sqle) {
            Util.rollbackConnection(conn);
            throw new RuntimeException(sqle);
        } finally {
            Jdbc.close(conn);
        }
    }

    private void makeCourseValidationsConnection(CourseGateway cg) throws SQLException,
            BusinessException {
        CourseDto old = cg.findById(c.id);
        if (old == null)
            throw new BusinessException("There doesn't exist a course " +
                    "with this ID.");
        if (old.startDate.before(Dates.today()))
            throw new BusinessException("Already begined courses cannot be " +
                    "updated");
        if (old.endDate.before(Dates.today()))
            throw new BusinessException("Already finished courses cannot be " +
                    "updated");
        if (!old.code.equals(c.code)) {
            if (cg.findByCode(c.code) != null) {
                throw new BusinessException("There already exists a " +
                        "course with this code.");
            }
        }
    }

    private void updateDedications(Connection conn) throws SQLException {
        DedicationGateway dg = new DedicationGateway(conn);

        List<DedicationDto> ds = dg.findByCourse(this.c.id);

        List<DedicationDto> toDelete = new ArrayList<>();

        for (DedicationDto d : ds) {
            if (!this.c.percentages.keySet().contains(d.vehicleType.id)) {
                dg.delete(d.id);
                toDelete.add(d);
            }
        }

        for (DedicationDto d : toDelete) {
            ds.remove(d);
        }

        for (Long id : this.c.percentages.keySet()) {
            Optional<DedicationDto> d =
                    ds.stream().filter(x -> x.vehicleType.id.equals(id)).findAny();
            DedicationDto dto;
            if (d.isPresent()) {
                dto = d.get();
                dto.percentage = this.c.percentages.get(id);
                dg.update(dto);
            } else {
                dto = new DedicationDto();
                dto.vehicleType = new VehicleTypeDto();
                dto.vehicleType.id = id;
                dto.course = this.c;
                dto.percentage = this.c.percentages.get(id);
                dg.add(dto);
            }

        }
    }

}
