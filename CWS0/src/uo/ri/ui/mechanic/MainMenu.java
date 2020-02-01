package uo.ri.ui.mechanic;

import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;

class MainMenu extends BaseMenu {

    private MainMenu() {
        menuOptions = new Object[][]{
                {"Mechanic", null},
                {"List work orders ", NotYetImplementedAction.class},
                {"Add spare part to a repair", NotYetImplementedAction.class},
                {"Delete spare part from a repair", NotYetImplementedAction.class},
                {"Close work order", NotYetImplementedAction.class},
        };
    }

    public static void main(String[] args) {
        new MainMenu().execute();
    }

}
