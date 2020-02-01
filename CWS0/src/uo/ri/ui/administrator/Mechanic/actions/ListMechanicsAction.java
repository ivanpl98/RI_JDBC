package uo.ri.ui.administrator.Mechanic.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.MechanicCrudService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.common.ServiceFactory;
import uo.ri.ui.administrator.Mechanic.Printer;

public class ListMechanicsAction implements Action {

    @Override
    public void execute() throws BusinessException {

        Console.println("\nMechanics List\n");
        MechanicCrudService mcs = ServiceFactory.getMechanicCrudService();

        for (MechanicDto m : mcs.findAllMechanics()) {
            Printer.printMechanic(m);
        }
    }

}