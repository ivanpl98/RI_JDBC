package uo.ri.ui.administrator.training.attendance.actions;

import alb.util.menu.Action;
import uo.ri.business.CourseAttendanceService;
import uo.ri.business.dto.EnrollmentDto;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.administrator.Printer;

import java.util.List;

public class ListAttendanceToCourseAction implements Action {

    private AttendanceUserInteractor user = new AttendanceUserInteractor();

    @Override
    public void execute() throws Exception {
        Long cId = user._askForCourseId();

        CourseAttendanceService cs =
                ServiceFactory.getCourseAttendanceService();
        List<EnrollmentDto> attendance = cs.findAttendanceByCourseId(cId);

        attendance.forEach(Printer::printAttendingMechanic);
    }

}
