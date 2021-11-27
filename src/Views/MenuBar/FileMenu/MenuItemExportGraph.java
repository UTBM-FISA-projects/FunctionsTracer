package Views.MenuBar.FileMenu;

import Views.Graph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Représente le menu exporter le graphique.
 *
 * @author Valentin DOREAU
 */
public class MenuItemExportGraph extends JMenuItem implements ActionListener {
    /**
     * Graphique à exporter
     */
    private final Graph graph;

    /**
     * Créé un menu pour exporter le graphique en image.
     *
     * @param graph Graphique à exporter
     */
    public MenuItemExportGraph(final Graph graph) {
        super("Exporter le graphique");
        this.graph = graph;
        addActionListener(this);
    }

    /**
     * Action déclenchée pour exporter le graph.
     *
     * @param actionEvent actionEvent
     * @see Graph#paintComponent(Graphics)
     */
    @Override
    public void actionPerformed(final ActionEvent actionEvent) {
        final JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            BufferedImage im = new BufferedImage(graph.getWidth(), graph.getHeight(), BufferedImage.TYPE_INT_ARGB);
            graph.paint(im.getGraphics());

            try {
                ImageIO.write(im, "PNG", fileChooser.getSelectedFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
