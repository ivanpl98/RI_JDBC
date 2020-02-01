package uo.ri.ui.foreman.reception.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.WorkOrderService;
import uo.ri.business.dto.CertificateDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.foreman.Printer;

import java.util.List;


public class ListCertifiedMechanicsAction implements Action {

    @Override
    public void execute() throws BusinessException {

        Long vtId = Console.readLong("Vehicle type id");

        WorkOrderService as = ServiceFactory.getWorkOrderService();
        List<CertificateDto> certs = as.findCertificatesByVehicleTypeId(vtId);

        Console.println("\nList of certified mechanics\n");
        for (CertificateDto m : certs) {
            Printer.printCertifiedMechanic(m);
        }

    }
}
