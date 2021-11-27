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
public class FileMenuExport extends JMenuItem implements ActionListener {

    private final ExpressionList expressionList;

    /**
     * Créer le sous-menu enregistrer-sous
     *
     * @param expressionList Liste d'expression sur laquelle interagir
     * @author Kilian GOËTZ
     */
    public FileMenuExport(ExpressionList expressionList) {
        super("Exporter");
        this.expressionList = expressionList;
        addActionListener(this);
    }

    /**
     * écris un fichier texte avec les expressions
     *
     * @param e ActionEvent
     * @author Kilian GOËTZ
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
