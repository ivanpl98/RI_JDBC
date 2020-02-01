package uo.ri.business.impl;

import uo.ri.business.CourseAttendanceService;
import uo.ri.business.dto.CourseDto;
import uo.ri.business.dto.EnrollmentDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;

import java.util.List;

public class CourseAttendanceServiceImpl implements CourseAttendanceService {
    @Override
    public EnrollmentDto registerNew(EnrollmentDto dto) throws BusinessException {
        return null;
    }

    @Override
    public void deleteAttendace(Long id) throws BusinessException {

    }

    @Override
    public List<EnrollmentDto> findAttendanceByCourseId(Long id) throws BusinessException {
        return null;
    }

    @Override
    public List<CourseDto> findAllActiveCourses() throws BusinessException {
        return null;
    }

    @Override
    public List<MechanicDto> findAllActiveMechanics() throws BusinessException {
        return null;
    }
}
