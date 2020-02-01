package uo.ri.ui.administrator.Mechanic.actions;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.MechanicCrudService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.common.ServiceFactory;

public class AddMechanicAction implements Action {

    @Override
    public void execute() throws Exception {
        MechanicDto mecanico = new MechanicDto();

        // Ask for mechanic data
        mecanico.dni = Console.readString("Dni");
        mecanico.name = Console.readString("Name");
        mecanico.surname = Console.readString("Surname");

        MechanicCrudService mcs = ServiceFactory.getMechanicCrudService();
        mcs.addMechanic(mecanico);

        // Show results
        Console.println("New mechanic added");
    }
}