package Views.MenuBar;

import Views.ExpressionList;
import Views.Graph;
import Views.MenuBar.FileMenu.MenuItemExport;
import Views.MenuBar.FileMenu.MenuItemExportGraph;
import Views.MenuBar.FileMenu.MenuItemOpen;

import javax.swing.*;

/**
 * Représente le menu fichier de l'application.<br>
 * Il permet d'exporter et de charger différents fichiers.
 *
 * @author Kilian GOËTZ
 */
public class MenuBar extends JMenuBar {
    /**
     * Créé un menu fichier.
     *
     * @param expressionList Liste d'expression avec laquelle interagir
     * @param graph          Graphique avec lequel interagir
     */
    public MenuBar(ExpressionList expressionList, Graph graph) {
        final JMenu fileMenu = new JMenu("Fichier");
        fileMenu.add(new MenuItemOpen(expressionList));
        fileMenu.add(new MenuItemExport(expressionList));
        fileMenu.add(new MenuItemExportGraph(graph));

        add(fileMenu);
    }
}
