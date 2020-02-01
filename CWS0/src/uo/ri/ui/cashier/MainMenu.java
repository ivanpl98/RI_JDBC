package uo.ri.ui.cashier;


import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.ui.cashier.action.WorkOrderBillingAction;
import uo.ri.ui.cashier.action.PayOffInvoiceAction;
import uo.ri.ui.cashier.action.UnchargedRepairsClientAction;

class MainMenu extends BaseMenu {

    private MainMenu() {
        menuOptions = new Object[][]{
                {"Cashier", null},
                {"Search not invoiced repairs by client", UnchargedRepairsClientAction.class},
                {"Search not invoiced repairs by plate", NotYetImplementedAction.class},
                {"Work order billing", WorkOrderBillingAction.class},
                {"Pay off invoice", PayOffInvoiceAction.class},
        };
    }

    public static void main(String[] args) {
        new MainMenu().execute();
    }

}
