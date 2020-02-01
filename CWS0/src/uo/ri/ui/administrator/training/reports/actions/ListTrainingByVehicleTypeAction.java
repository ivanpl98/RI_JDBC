package uo.ri.ui.administrator.training.reports.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.CourseReportService;
import uo.ri.business.dto.TrainingHoursRow;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.administrator.Printer;

import java.util.Comparator;
import java.util.List;


public class ListTrainingByVehicleTypeAction implements Action {

    @Override
    public void execute() throws Exception {

        CourseReportService rs = ServiceFactory.getCourseReportService();
        List<TrainingHoursRow> rows = rs.findTrainingByVehicleTypeAndMechanic();

        Console.println("Training by vehicle type");
        rows.sort(new TVTRComparator());
        rows.forEach(Printer::printTrainingHoursRow
        );
    }

    /**
     * The sorting can be done in the query, but is also frequently done
     * at the presentation layer
     */
    private class TVTRComparator implements Comparator<TrainingHoursRow> {

        @Override
        public int compare(TrainingHoursRow a,
                           TrainingHoursRow b) {

            int res = a.vehicleTypeName.compareTo(b.vehicleTypeName);
            if (res == 0) {
                res = a.mechanicFullName.compareTo(b.mechanicFullName);
            }
            return res;
        }

    }

}
