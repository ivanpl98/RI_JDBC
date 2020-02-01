package uo.ri.ui.foreman.reception.actions;

import alb.util.console.Console;
import uo.ri.business.VehicleCrudService;
import uo.ri.business.dto.VehicleDto;
import uo.ri.business.dto.WorkOrderDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;


class WorkOrderUserInteractor {

    WorkOrderDto askForWorkOrder(VehicleDto v) {
        WorkOrderDto wo = new WorkOrderDto();
        wo.description = Console.readString("Work description");
        wo.vehicleId = v.id;
        return wo;
    }

    VehicleDto askForVehicle() throws BusinessException {
        String plate = Console.readString("Plate number");

        VehicleCrudService vs = ServiceFactory.getVehicleCrudService();

        return vs.findVehicleByPlate(plate);
    }

}
