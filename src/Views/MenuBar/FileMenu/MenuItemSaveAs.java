package Views.MenuBar.FileMenu;

import Views.Expression;
import Views.ExpressionList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * Représente le sous-menu enregistrer-sous
 *
 * @author Kilian GOËTZ
 */

public class MenuItemSaveAs extends JMenuItem implements ActionListener {

    private Image img;
    private ExpressionList expressionList;

    /**
     *
     * Créer le sous-menu enregistrer-sous
     *
     * @author Kilian GOËTZ
     * @param expressionList
     */

    public MenuItemSaveAs(ExpressionList expressionList){
        super("Enregistrer-sous");
        this.expressionList = expressionList;
        addActionListener(this);
    }

    /**
     *
     * écris un fichier texte avec les expressions
     *
     * @author Kilian GOËTZ
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            int length = expressionList.getComponentCount();
            File f = fileChooser.getSelectedFile();
            try {
                FileWriter fw = new FileWriter(f,true);
                for(int i=0; i<length-1;i++){
                    String a = ((Expression)expressionList.getComponent(i)).getExpression();
                    System.out.println(a);
                    fw.append(a).append("\r\n");
                }
                fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
