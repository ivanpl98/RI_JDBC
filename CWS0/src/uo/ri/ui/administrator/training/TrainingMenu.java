package uo.ri.ui.administrator.training;

import alb.util.menu.BaseMenu;
import uo.ri.ui.administrator.training.attendance.AttendanceMenu;
import uo.ri.ui.administrator.training.certificates.GenerateCertificatesAction;
import uo.ri.ui.administrator.training.course.CourseCrudMenu;
import uo.ri.ui.administrator.training.reports.ReportsMenu;


public class TrainingMenu extends BaseMenu {

    public TrainingMenu() {
        menuOptions = new Object[][]{
                {"Manager > Training management", null},

                {"Course management", CourseCrudMenu.class},
                {"Attendance registration", AttendanceMenu.class},
                {"Reports", ReportsMenu.class},
                {"", null},
                {"Certificate generation", GenerateCertificatesAction.class},

        };
    }

}
