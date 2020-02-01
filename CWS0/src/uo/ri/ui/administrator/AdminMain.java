package uo.ri.ui.administrator;

import alb.util.console.Printer;
import alb.util.menu.BaseMenu;
import uo.ri.ui.administrator.Mechanic.MechanicsMenu;
import uo.ri.ui.administrator.sparepart.SparepartsMenu;
import uo.ri.ui.administrator.training.TrainingMenu;
import uo.ri.ui.administrator.vehicletype.VehicleTypesMenu;


class AdminMain {

    private static class MainMenu extends BaseMenu {
        MainMenu() {
            menuOptions = new Object[][]{
                    {"Manager", null},

                    {"Mechanics management", MechanicsMenu.class},
                    {"Spareparts management", SparepartsMenu.class},
                    {"Vehicle types management", VehicleTypesMenu.class},
                    {"Training management", TrainingMenu.class},
            };
        }
    }

    public static void main(String[] args) {
        new AdminMain()
                .run()
                .close();
    }


    private AdminMain run() {
        try {
            new MainMenu().execute();

        } catch (RuntimeException rte) {
            Printer.printRuntimeException(rte);
        }
        return this;
    }

    private void close() {

    }

}
