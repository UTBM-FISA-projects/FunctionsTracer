package Views.MenuBar.EditMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemRedo extends JMenuItem implements ActionListener {

    public MenuItemRedo(){
        super("Rétablir");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
