package uo.ri.ui.foreman.reception.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.WorkOrderService;
import uo.ri.business.dto.WorkOrderDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.foreman.Printer;

import java.util.Optional;


public class ViewWorkOrderDetailAction implements Action {

    @Override
    public void execute() throws BusinessException {

        Long woId = Console.readLong("Work order id");

        WorkOrderService as = ServiceFactory.getWorkOrderService();
        Optional<WorkOrderDto> oWo = as.findWorkOrderById(woId);

        if (oWo.isPresent()) {
            Printer.printWorkOrderDetail(oWo.get());
        } else {
            Console.println("There is no work order with that id");
        }

    }
}
