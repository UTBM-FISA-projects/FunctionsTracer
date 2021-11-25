package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpressionList extends JPanel implements ActionListener {

    private final BoxLayout layout;
    private final JButton addButton;
    private final Graph graph;

    public ExpressionList(Graph graph) {
        this.graph = graph;

        layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layout);
        add(new Expression(graph, new ActionDelete()));

        Image img = getToolkit().getImage("resources/plus.png");
        Image newimg = img.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
        Icon icon = new ImageIcon(newimg);
        addButton = new ExpressionButtons(icon);
        addButton.addActionListener(this);
        add(addButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        remove(addButton);
        add(new Expression(graph, new ActionDelete()));
        add(addButton);
        revalidate();
    }

    public class ActionDelete extends AbstractAction {
        private Expression element = null;

        public void setElement(final Expression element) {
            this.element = element;
        }

        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            if (element != null) {
                remove(element);
                revalidate();
                graph.repaint();
            }
        }
    }
}
