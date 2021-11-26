package Views.MenuBar.FileMenu;

import Views.ExpressionList;

import javax.swing.*;

public class FileMenu extends JMenu {

    public FileMenu(ExpressionList expressionList) {
        super("Fichier");
        add(new FileMenuExport(expressionList));
        add(new MenuItemOpen(expressionList));
    }
}
