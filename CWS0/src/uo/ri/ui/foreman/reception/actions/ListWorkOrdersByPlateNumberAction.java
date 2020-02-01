package uo.ri.ui.foreman.reception.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.WorkOrderService;
import uo.ri.business.dto.WorkOrderDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.foreman.Printer;

import java.util.List;


public class ListWorkOrdersByPlateNumberAction implements Action {

    @Override
    public void execute() throws BusinessException {

        String plate = Console.readString("Plate number");

        WorkOrderService as = ServiceFactory.getWorkOrderService();
        List<WorkOrderDto> wos = as.findWorkOrdersByPlateNumber(plate);

        Console.println("Work orders for vehicle " + plate);
        for (WorkOrderDto wo : wos) {
            Printer.printWorkOrderDetail(wo);
        }

    }
}
