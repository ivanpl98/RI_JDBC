package uo.ri.business.impl;

import uo.ri.business.CourseCrudService;
import uo.ri.business.dto.CourseDto;
import uo.ri.business.dto.VehicleTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.administrator.course.*;

import java.util.List;

public class CourseCrudServiceImpl implements CourseCrudService {
    @Override
    public CourseDto registerNew(CourseDto dto) throws BusinessException {
        AddCourse ac = new AddCourse(dto);
        return ac.execute();
    }

    @Override
    public void updateCourse(CourseDto dto) throws BusinessException {
        UpdateCourse uc = new UpdateCourse(dto);
        uc.execute();
    }

    @Override
    public void deleteCourse(Long id) throws BusinessException {
        DeleteCourse dc = new DeleteCourse(id);
        dc.execute();
    }

    @Override
    public List<CourseDto> findAllCourses() {
        FindAll fa = new FindAll();
        return fa.execute();
    }

    @Override
    public List<VehicleTypeDto> findAllVehicleTypes() {
        FindVehicleTypes fvt = new FindVehicleTypes();
        return fvt.execute();
    }

    @Override
    public CourseDto findCourseById(Long cId) {
        FindById fn = new FindById(cId);
        return fn.execute();
    }
}
