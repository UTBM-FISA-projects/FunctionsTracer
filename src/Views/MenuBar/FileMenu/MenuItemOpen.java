package Views.MenuBar.FileMenu;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuItemOpen extends JMenuItem implements ActionListener {

    private Image img;

    public MenuItemOpen() {
        super("Ouvrir");
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
