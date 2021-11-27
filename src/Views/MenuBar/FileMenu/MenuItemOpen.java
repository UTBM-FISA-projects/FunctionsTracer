package Views.MenuBar.FileMenu;


import Views.ExpressionList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Représente le sous-menu ouvrir qui permet de charger des expressions depuis un fichier.
 *
 * @author Valentin DOREAU
 */
public class MenuItemOpen extends JMenuItem implements ActionListener {

    /**
     * Liste d'expression avec laquelle interagir.
     */
    private final ExpressionList expressionList;

    /**
     * Créé un sous-menu ouvrir.
     *
     * @param expressionList Liste d'expression à modifier
     */
    public MenuItemOpen(ExpressionList expressionList) {
        super("Ouvrir");
        addActionListener(this);
        this.expressionList = expressionList;
    }

    /**
     * Action invoquée pour charger les expressions dans la liste.
     *
     * @param e ActionEvent - inutilisé
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            final Scanner sc;

            try {
                sc = new Scanner(fileChooser.getSelectedFile());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return;
            }

            while (sc.hasNextLine()) {
                expressionList.addExpression(sc.nextLine());
            }
            sc.close();
        }
    }
}
