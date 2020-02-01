package uo.ri.ui.cashier.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.InvoiceService;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.cashier.Printer;

import java.util.ArrayList;
import java.util.List;

public class WorkOrderBillingAction implements Action {

    @Override
    public void execute() throws BusinessException {
        List<Long> workOrderIds = new ArrayList<>();

        // type work order ids to be invoiced in the invoice
        do {
            Long id = Console.readLong("Type work order ids ? ");
            workOrderIds.add(id);
        } while (nextWorkorder());

        InvoiceService is = ServiceFactory.getInvoiceService();
        InvoiceDto i = is.createInvoiceFor(workOrderIds);

        Printer.printInvoice(i);

    }


    private boolean nextWorkorder() {
        return Console.readString(" Any other workorder? (y/n) ").equalsIgnoreCase("y");
    }

}
