package uo.ri.ui.foreman.reception.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.WorkOrderService;
import uo.ri.business.dto.VehicleDto;
import uo.ri.business.dto.WorkOrderDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.foreman.Printer;

public class RegisterWorkOrderAction implements Action {

    private WorkOrderUserInteractor user = new WorkOrderUserInteractor();

    @Override
    public void execute() throws BusinessException {

        VehicleDto v = user.askForVehicle();
        Printer.printVehicleDetail(v);

        WorkOrderDto wo = user.askForWorkOrder(v);

        WorkOrderService as = ServiceFactory.getWorkOrderService();
        as.registerNew(wo);

        Console.println("\nWork order registered: " + wo.id);
    }

}
