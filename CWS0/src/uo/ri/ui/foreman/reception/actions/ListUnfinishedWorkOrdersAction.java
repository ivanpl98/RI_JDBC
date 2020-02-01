package uo.ri.ui.foreman.reception.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.WorkOrderService;
import uo.ri.business.dto.WorkOrderDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.foreman.Printer;

import java.util.List;


public class ListUnfinishedWorkOrdersAction implements Action {

    @Override
    public void execute() throws BusinessException {

        WorkOrderService as = ServiceFactory.getWorkOrderService();
        List<WorkOrderDto> wos = as.findUnfinishedWorkOrders();

        Console.println("In process work orders");
        for (WorkOrderDto wo : wos) {
            Printer.printWorkOrderDetail(wo);
        }

    }
}
