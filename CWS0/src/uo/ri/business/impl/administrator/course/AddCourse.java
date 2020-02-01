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
import uo.ri.persistence.VehicleTypeGateway;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class AddCourse {

    private CourseDto c;

    public AddCourse(CourseDto c) {
        this.c = c;
    }

    public CourseDto execute() throws BusinessException {
        makeCourseValidationsNoConnection(this.c);
        Connection conn = null;
        CourseGateway cg;
        try {
            conn = Jdbc.getConnection();
            conn.setAutoCommit(false);
            cg = new CourseGateway(conn);
            makeCourseValidationsConnection(conn, cg);
            cg.add(c);
            createDedications(conn);
            conn.commit();
            return cg.findByName(c.name);
        } catch (SQLException sqle) {
            Util.rollbackConnection(conn);
            throw new RuntimeException(sqle);
        } finally {
            Jdbc.close(conn);
        }
    }

    private void createDedications(Connection conn) throws SQLException {
        DedicationGateway dg = new DedicationGateway(conn);

        for (Long id : this.c.percentages.keySet()) {
            DedicationDto dto = new DedicationDto();
            dto.vehicleType = new VehicleTypeDto();
            dto.vehicleType.id = id;
            dto.course = this.c;
            dto.percentage = this.c.percentages.get(id);
            dg.add(dto);
        }

    }

    static void makeCourseValidationsNoConnection(CourseDto c) throws BusinessException {
        makeStringValidations(c);
        makeDateValidations(c);
        makeHoursValidation(c);
        makePercentageValidations(c);
    }

    private static void makeStringValidations(CourseDto c) throws BusinessException {
        if (stringEmpty(c.name))
            throw new BusinessException("Name of the course cannot be empty");
        else if (stringEmpty(c.description))
            throw new BusinessException("Description of the course cannot be " +
                    "empty");
        else if (stringEmpty(c.code))
            throw new BusinessException("Code of the course cannot be " +
                    "empty");
    }

    private static void makeDateValidations(CourseDto c) throws BusinessException {
        Date today = Dates.today();

        if (c.startDate == null)
            throw new BusinessException("Start date of the course cannot be " +
                    "empty");
        else if (c.endDate == null)
            throw new BusinessException("End date of the course cannot be " +
                    "empty");
        else if (c.startDate.before(today) || c.endDate.before(today)) {
            throw new BusinessException("Dates of the course cannot be in the" +
                    " past");
        } else if (c.endDate.before(c.startDate)) {
            throw new BusinessException("End date of the course cannot be " +
                    "before the start date");
        }
    }

    private static void makeHoursValidation(CourseDto c) throws BusinessException {
        if (!(c.hours > 0))
            throw new BusinessException("Total amount of hours of the course " +
                    "cannot be empty or negative");
    }

    private static void makePercentageValidations(CourseDto c) throws BusinessException {
        if (c.percentages == null || c.percentages.size() == 0)
            throw new BusinessException("Percentages of dedication of the " +
                    "course cannot be empty");
        int total = 0;
        for (Long id : c.percentages.keySet()) {
            int perc = c.percentages.get(id);
            if (!(perc > 0))
                throw new BusinessException("There cannot be percentages of " +
                        "dedication of the course empty or negative");
            total += perc;
        }
        if (total != 100)
            throw new BusinessException("Total percentage of dedication of " +
                    "the course cannot be different from 100%");
    }

    private void makeCourseValidationsConnection(Connection conn,
                                                 CourseGateway cg) throws SQLException,
            BusinessException {
        VehicleTypeGateway vtg = new VehicleTypeGateway(conn);
        if (cg.findByName(this.c.name) != null)
            throw new BusinessException("There already exists a course " +
                    "with the same name.");
        for (Long id : this.c.percentages.keySet()) {
            VehicleTypeDto vt = vtg.findById(id);
            if (vt == null)
                throw new BusinessException("Percentage dedicated to non " +
                        "existing vehicle type");
        }
    }

    private static boolean stringEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}