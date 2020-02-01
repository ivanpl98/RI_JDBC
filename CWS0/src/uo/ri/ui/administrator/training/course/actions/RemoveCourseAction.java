package uo.ri.ui.administrator.training.course.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.CourseCrudService;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;


public class RemoveCourseAction implements Action {

    @Override
    public void execute() throws BusinessException {

        // Ask the user for data
        Long cId = Console.readLong("Course id");

        // Invoke the service
        CourseCrudService cs = ServiceFactory.getCourseCrudService();
        cs.deleteCourse(cId);

        // Show result
        Console.println("Course removed");
    }

}
