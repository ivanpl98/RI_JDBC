package uo.ri.ui.administrator.training.reports.actions;

import alb.util.console.Console;
import uo.ri.business.CourseAttendanceService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.administrator.Printer;

import java.util.List;


class ReportsUserInteractor {

    Long _askForMechanicId() throws BusinessException {
        showMechanics();
        return Console.readLong("Mechanic id");
    }

    private void showMechanics() throws BusinessException {
        CourseAttendanceService cs =
                ServiceFactory.getCourseAttendanceService();
        List<MechanicDto> mechanics = cs.findAllActiveMechanics();
        Console.println("List of mechanics");
        mechanics.forEach(Printer::printMechanic);
    }

}
