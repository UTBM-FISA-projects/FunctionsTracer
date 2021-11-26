package Views.MenuBar;

import Views.ExpressionList;
import Views.MenuBar.FileMenu.FileMenu;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar(ExpressionList expressionList) {
        add(new FileMenu(expressionList));
    }
}
