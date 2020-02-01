package uo.ri.ui.foreman;

import alb.util.console.Printer;
import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.ui.foreman.reception.ReceptionMenu;


class ForemanMain {

    private static class MainMenu extends BaseMenu {
        MainMenu() {
            menuOptions = new Object[][]{
                    {"Foreman", null},
                    {"Vehicle reception", ReceptionMenu.class},
                    {"Client management", NotYetImplementedAction.class},
                    {"Vehicle management", NotYetImplementedAction.class},
                    {"Review client history", NotYetImplementedAction.class},
            };
        }
    }

    public static void main(String[] args) {
        new ForemanMain()
                .run()
                .close();
    }


    private ForemanMain run() {
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
