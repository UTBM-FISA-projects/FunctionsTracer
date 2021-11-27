package Views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Représente une liste d'expression avec un bouton d'ajout et une barre de défilement.
 *
 * @author Kilian GOËTZ
 */
public class ExpressionList extends JPanel implements ActionListener {
    /**
     * Bouton d'ajout d'expression.
     */
    private final JButton addButton;
    /**
     * Graphique avec les courbes correspondantes.
     */
    private final Graph graph;

    /**
     * Créé une liste d'expressions avec un graphique associé.
     *
     * @param graph Graphique affichant les courbes associées
     */
    public ExpressionList(Graph graph) {
        this.graph = graph;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new Expression(graph, new ActionDelete()));

        addButton = new ExpressionButtons(
            "Ajouter une expression", getClass().getResource("plus.png"),
            40,
            40
        );
        addButton.addActionListener(this);
        add(addButton);
    }

    /**
     * Ajoute une expression à la liste.
     *
     * @param expr Expression à ajouter
     * @see Views.MenuBar.FileMenu.MenuItemOpen
     */
    public void addExpression(String expr) {
        remove(addButton);
        Expression expression = new Expression(graph, new ActionDelete());
        expression.setExpression(expr);
        add(expression);
        add(addButton);
        revalidate();
    }

    /**
     * Action pour ajouter une expression à la liste.
     *
     * @param e inutilisé
     */
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
            if (element != null && graph.removeExpression(element.hashCode())) {
                remove(element);
                revalidate();
            }
        }
    }
}
