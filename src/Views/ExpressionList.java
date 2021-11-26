package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpressionList extends JPanel implements ActionListener {

    private final JButton addButton;
    private final Graph graph;

    public ExpressionList(Graph graph) {
        this.graph = graph;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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

    /**
     * Gère les demandes de suppression d'une {@link Expression}.
     *
     * @author Valentin DOREAU
     */
    public class ActionDelete extends AbstractAction {
        /**
         * {@link Expression} concernée par l'action.
         */
        private Expression element = null;

        /**
         * Définie l'élément concerné par l'action
         *
         * @param element {@link Expression} à supprimer
         */
        public void setElement(final Expression element) {
            this.element = element;
        }

        /**
         * Action déclenchée à la demande de suppression d'une {@link Expression}.
         *
         * @param actionEvent Évènement
         */
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
