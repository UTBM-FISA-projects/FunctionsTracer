package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpressionList extends JPanel implements ActionListener {

    private final GridLayout layout;
    private final JButton addButton;
    private final Graph graph;

    public ExpressionList(Graph graph) {
        this.graph = graph;

        layout = new GridLayout(2, 1, 3, 3);
        setLayout(layout);
        add(new Expression(graph));

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
        add(new Expression(graph));
        add(addButton);
        revalidate();
    }
}
