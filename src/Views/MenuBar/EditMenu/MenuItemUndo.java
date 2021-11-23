package Views.MenuBar.EditMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemUndo extends JMenuItem implements ActionListener {

    public MenuItemUndo(){
        super("Annuler");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
