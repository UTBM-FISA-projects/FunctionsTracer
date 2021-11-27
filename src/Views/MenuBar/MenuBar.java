package Views.MenuBar;

import Views.ExpressionList;
import Views.Graph;
import Views.MenuBar.FileMenu.MenuItemExport;
import Views.MenuBar.FileMenu.MenuItemExportGraph;
import Views.MenuBar.FileMenu.MenuItemOpen;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    public MenuBar(ExpressionList expressionList, Graph graph) {
        final JMenu fileMenu = new JMenu("Fichier");
        fileMenu.add(new MenuItemOpen(expressionList));
        fileMenu.add(new MenuItemExport(expressionList));
        fileMenu.add(new MenuItemExportGraph(graph));

        add(fileMenu);
    }
}
