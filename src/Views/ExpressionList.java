package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpressionList extends JPanel implements ActionListener {

    private GridLayout layout;
    private JButton addButton;

    public ExpressionList() {

        layout = new GridLayout(2, 1, 3, 3);
        setLayout(layout);
        add(new Expression());

        Image img = getToolkit().getImage("resources/plus.png");
        Image newimg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        Icon icon = new ImageIcon(newimg);
        addButton = new ExpressionButtons(icon);
        addButton.addActionListener(this);
        add(addButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        layout.setRows(layout.getRows() + 1);
        remove(addButton);
        add(new Expression());
        add(addButton);
        revalidate();
    }
}
