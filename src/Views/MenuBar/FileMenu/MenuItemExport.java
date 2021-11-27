package Views.MenuBar.FileMenu;

import Views.Expression;
import Views.ExpressionList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Représente le sous-menu enregistrer-sous
 *
 * @author Kilian GOËTZ
 */
public class MenuItemExport extends JMenuItem implements ActionListener {

    /**
     * Liste d'expression à exporter
     */
    private final ExpressionList expressionList;

    /**
     * Créer le sous-menu enregistrer-sous
     *
     * @param expressionList Liste d'expression sur laquelle interagir
     */
    public MenuItemExport(ExpressionList expressionList) {
        super("Exporter");
        this.expressionList = expressionList;
        addActionListener(this);
    }

    /**
     * Sauvegarde les expressions dans un fichier texte.
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            int length = expressionList.getComponentCount();
            File f = fileChooser.getSelectedFile();
            try {
                FileWriter fw = new FileWriter(f, true);
                for (int i = 0; i < length - 1; i++) {
                    String a = ((Expression) expressionList.getComponent(i)).getText();
                    fw.append(a).append("\r\n");
                }
                fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
