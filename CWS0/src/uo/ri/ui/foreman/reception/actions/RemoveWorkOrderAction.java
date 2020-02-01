package uo.ri.ui.foreman.reception.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.WorkOrderService;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;


public class RemoveWorkOrderAction implements Action {

    @Override
    public void execute() throws BusinessException {

        Long woId = Console.readLong("Work order id");

        WorkOrderService as = ServiceFactory.getWorkOrderService();
        as.deleteWorkOrder(woId);

        Console.println("\nThe work order has been deleted");
    }
}
