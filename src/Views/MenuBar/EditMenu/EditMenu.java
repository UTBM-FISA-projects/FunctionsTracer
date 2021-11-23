package Views.MenuBar.EditMenu;

import javax.swing.*;

public class EditMenu extends JMenu {
    public EditMenu(){
        super("Éditer");
        add(new MenuItemUndo());
        add(new MenuItemRedo());
    }
}
