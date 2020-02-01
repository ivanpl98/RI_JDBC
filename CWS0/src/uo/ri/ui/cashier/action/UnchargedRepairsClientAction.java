package uo.ri.ui.cashier.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.InvoiceService;
import uo.ri.business.dto.WorkOrderDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.cashier.Printer;

import java.util.List;

public class UnchargedRepairsClientAction implements Action {

    /**
     * Process:
     * <p>
     * - Ask customer dni
     * <p>
     * - Display all uncharged breakdowns
     * (status <> 'CHARGED'). For each breakdown, display
     * id, date, status, amount and description
     */
    @Override
    public void execute() throws BusinessException {
        String dni;
        do {
            dni = Console.readString("Type client dni");
        } while (dni == null || dni.trim().length() == 0);
        InvoiceService is = ServiceFactory.getInvoiceService();
        List<WorkOrderDto> wos = is.findRepairsByClient(dni);
        wos.forEach(Printer::printWorkOrder);
    }

}
