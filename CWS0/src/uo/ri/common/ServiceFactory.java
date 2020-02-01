package uo.ri.common;

import uo.ri.business.*;
import uo.ri.business.impl.*;

public class ServiceFactory {

    public static MechanicCrudService getMechanicCrudService() {
        return new MechanicCrudServiceImpl();
    }

    public static CertificateService getCertificateService() {
        return new CertificateServiceImpl();
    }

    public static CourseReportService getCourseReportService() {
        return new CourseReportServiceImpl();
    }

    public static CourseCrudService getCourseCrudService() {
        return new CourseCrudServiceImpl();
    }

    public static CourseAttendanceService getCourseAttendanceService() {
        return new CourseAttendanceServiceImpl();
    }

    public static InvoiceService getInvoiceService() {
        return new InvoiceServiceImpl();
    }

    public static WorkOrderService getWorkOrderService() {
        return new WorkOrderServiceImpl();
    }

    public static VehicleCrudService getVehicleCrudService() {
        return null;
    }
}
