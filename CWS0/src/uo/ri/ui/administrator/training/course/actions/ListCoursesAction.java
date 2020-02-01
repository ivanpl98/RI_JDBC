package uo.ri.ui.administrator.training.course.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.CourseCrudService;
import uo.ri.business.dto.CourseDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.administrator.Printer;

import java.util.List;

public class ListCoursesAction implements Action {

    @Override
    public void execute() throws BusinessException {

        CourseCrudService cs = ServiceFactory.getCourseCrudService();
        List<CourseDto> courses = cs.findAllCourses();

        Console.println("\nList of courses\n");
        for (CourseDto c : courses) {
            Printer.printCourse(c);
        }

    }
}
