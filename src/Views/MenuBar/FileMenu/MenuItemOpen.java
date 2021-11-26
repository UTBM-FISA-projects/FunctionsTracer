package Views.MenuBar.FileMenu;


import Views.ExpressionList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MenuItemOpen extends JMenuItem implements ActionListener {

    private final ExpressionList expressionList;

    public MenuItemOpen(ExpressionList expressionList) {
        super("Ouvrir");
        addActionListener(this);
        this.expressionList = expressionList;
    }

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
