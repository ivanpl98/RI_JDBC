package uo.ri.ui.administrator.training.attendance.actions;

import alb.util.console.Console;
import uo.ri.business.CourseAttendanceService;
import uo.ri.business.dto.CourseDto;
import uo.ri.business.dto.EnrollmentDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.administrator.Printer;

import java.util.List;

class AttendanceUserInteractor {

    void _fill(EnrollmentDto att) throws BusinessException {
        fillCourseId(att);
        fillMechanicId(att);
        fillAttendance(att);
        fillPassed(att);
    }

    private void fillAttendance(EnrollmentDto att) {
        att.attendance = Console.readInt("% of attendance");
    }

    private void fillMechanicId(EnrollmentDto att) throws BusinessException {
        showMechanics();
        att.mechanicId = Console.readString("Mechanic id");
    }

    private void fillCourseId(EnrollmentDto att) throws BusinessException {
        showCourses();
        att.courseId = Console.readString("Course id");
    }

    private void fillPassed(EnrollmentDto att) {
        att.passed = Console.readString("Passed (y/n)?").equalsIgnoreCase("y");
    }

    private void showMechanics() throws BusinessException {
        CourseAttendanceService cs =
                ServiceFactory.getCourseAttendanceService();
        List<MechanicDto> mechanics = cs.findAllActiveMechanics();
        Console.println("List of mechanics");
        mechanics.forEach(Printer::printMechanic);
    }

    private void showCourses() throws BusinessException {
        CourseAttendanceService cs =
                ServiceFactory.getCourseAttendanceService();
        List<CourseDto> mechanics = cs.findAllActiveCourses();
        Console.println("List of courses");
        mechanics.forEach(Printer::printCourse);
    }

    Long _askForCourseId() throws BusinessException {
        showCourses();
        return Console.readLong("Course id");
    }

}
