package uo.ri.ui.administrator;

import alb.util.console.Console;
import uo.ri.business.dto.*;

public class Printer {

    public static void printMechanic(MechanicDto m) {

        Console.printf("\t%d %-10.10s %-15.15s %-25.25s\n"
                , m.id
                , m.dni
                , m.name
                , m.surname
        );
    }

    public static void printVehicleType(VehicleTypeDto vt) {

        Console.printf("\t%d %-10.10s %5.2f %d\n"
                , vt.id
                , vt.name
                , vt.pricePerHour
                , vt.minTrainigHours
        );
    }

    public static void printCourse(CourseDto c) {

        Console.printf("%d\t%s %s %-35.35s %td/%<tm/%<tY %td/%<tm/%<tY %d\n"
                , c.id
                , c.code
                , c.name
                , c.description
                , c.startDate
                , c.endDate
                , c.hours
        );
        c.percentages.forEach((id, percent) ->
                Console.printf("\t %d %d percent\n", id, percent)
        );
    }

    public static void printAttendingMechanic(EnrollmentDto att) {
        Console.printf("%-30.30s\t%d\t%s\n"
                , att.mechanic.surname + ", " + att.mechanic.name
                , att.attendance
                , att.passed ? "passed" : "failed"
        );
    }

    public static void printTrainingForMechanic(TrainingForMechanicRow row) {

        Console.printf("\t%-20.20s\t%d\t%d\n"
                , row.vehicleTypeName
                , row.enrolledHours
                , row.attendedHours
        );

    }

    public static void printTrainingHoursRow(TrainingHoursRow r) {

        Console.printf("%-20.20s\t%-30.30s\t%d hours\n"
                , r.vehicleTypeName
                , r.mechanicFullName
                , r.enrolledHours);
    }

    public static void printCertificateRow(CertificateDto r) {

        Console.printf("%-20.20s\t%-30.30s\t from %td/%<tm/%<tY\n"
                , r.vehicleType.name
                , r.mechanic.surname + ", " + r.mechanic.name
                , r.obtainedAt);
    }

}
