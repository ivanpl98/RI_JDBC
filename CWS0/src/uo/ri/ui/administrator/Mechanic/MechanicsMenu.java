package uo.ri.ui.administrator.Mechanic;


import alb.util.menu.BaseMenu;
import uo.ri.ui.administrator.Mechanic.actions.AddMechanicAction;
import uo.ri.ui.administrator.Mechanic.actions.DeleteMechanicAction;
import uo.ri.ui.administrator.Mechanic.actions.ListMechanicsAction;
import uo.ri.ui.administrator.Mechanic.actions.UpdateMechanicAction;

public class MechanicsMenu extends BaseMenu {

    public MechanicsMenu() {
        menuOptions = new Object[][]{
                {"Administrator -> Mechanics management", null},

                {"Add mechanic", AddMechanicAction.class},
                {"Delete mechanic", DeleteMechanicAction.class},
                {"List mechanic", ListMechanicsAction.class},
                {"Update mechanic", UpdateMechanicAction.class},
        };
    }
}