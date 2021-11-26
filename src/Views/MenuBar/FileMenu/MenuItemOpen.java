package Views.MenuBar.FileMenu;


import Views.Expression;
import Views.ExpressionList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MenuItemOpen extends JMenuItem implements ActionListener {

    private Image img;
    private ExpressionList expressionList;

    public MenuItemOpen() {
        super("Ouvrir");
        addActionListener(this);
        this.expressionList = expressionList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            int length = expressionList.getComponentCount();
            File f = fileChooser.getSelectedFile();
            try {
                FileReader fw = new FileReader(f);
                for (int i = 0; i < length - 1; i++) {
                    String a = ((Expression) expressionList.getComponent(i)).getExpression();

                }
                fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
