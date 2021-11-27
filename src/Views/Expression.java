package Views;

import Controllers.Parser.Parser;
import Exceptions.MalformedExpressionException;
import Exceptions.MismatchParenthesisException;
import Views.ValueTable.ValueTableFrame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.EmptyStackException;

/**
 * Représente l'ensemble des boutons liés à une expression et un champ texte.
 *
 * @author Kilian GOËTZ
 */
public class Expression extends JPanel {

    /**
     * Champ texte contenant l'expression
     */
    private final TextField textField;
    /**
     * Graphique sur lequel interagir.
     */
    private final Graph graph;
    /**
     * Couleur de la courbe. Par défaut rouge.
     */
    private Color color = Color.RED;

    /**
     * Créé un panel contenant tous les éléments d'une expression.
     *
     * @param graph        Graphique sur lequel tracer les courbes.
     * @param actionDelete Action à effectuer à la suppression
     */
    public Expression(Graph graph, ExpressionList.ActionDelete actionDelete) {
        super();

        this.graph = graph;

        setLayout(new FlowLayout());

        //Bouton Choix couleur
        JButton delButton = new ExpressionButtons("Couleur de la courbe", getClass().getResource("color.png"));
        delButton.addActionListener(new ActionColor());
        add(delButton);

        //Textfield
        textField = new TextField();
        textField.setForeground(color);
        // écoute les changements
        textField.getDocument().addDocumentListener(new ExpressionListener());
        add(textField);

        //Bouton Supprimer
        JButton colorButton = new ExpressionButtons(
            "Supprimer l'expression", getClass().getResource("trash.png")
        );
        actionDelete.setElement(this);
        colorButton.addActionListener(actionDelete);
        add(colorButton);

        //Bouton Tableau
        JButton tabButton = new ExpressionButtons(
            "Tableau de valeurs", getClass().getResource("table.png")
        );
        tabButton.addActionListener(new ActionTable());
        add(tabButton);
    }

    /**
     * Récupère l'expression affichée dans le champ texte.
     *
     * @return L'expression affichée
     */
    public String getText() {
        return textField.getText();
    }

    /**
     * Défini l'expression affichée dans le champ texte.
     *
     * @param expression Expression à afficher
     */
    public void setExpression(String expression) {
        textField.setText(expression);
    }

    /**
     * Met à jour la courbe correspondante à l'expression.
     *
     * @see Graph
     * @see Parser
     */
    private void updateExpression() {
        try {
            Parser parser = new Parser(textField.getText());
            final Controllers.Operands.Expression expr = parser.parse();
            graph.addExpression(hashCode(), expr, color);
        } catch (MismatchParenthesisException | MalformedExpressionException | EmptyStackException ignored) {
            graph.removeExpression(hashCode());
        }
    }

    /**
     * Action pour le tableau de valeurs.
     *
     * @author Kilian GOËTZ
     */
    private class ActionTable extends AbstractAction {
        /**
         * Affiche le tableau de valeurs correspondant à l'expression entrée.
         *
         * @param e inutilisé
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new ValueTableFrame(new Parser(textField.getText()).parse());
            } catch (MismatchParenthesisException | MalformedExpressionException | EmptyStackException ignored) {
            }
        }
    }

    /**
     * Action pour choisir la couleur de la courbe d'une expression.
     *
     * @author Kilian GOËTZ
     * @author Valentin DOREAU
     */
    private class ActionColor extends AbstractAction {
        /**
         * Affiche une fenêtre pour choisir une couleur puis défini la couleur de la courbe.
         *
         * @param e inutilisé
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            final Color tmp = JColorChooser.showDialog(
                null,
                "Couleur de la courbe",
                color
            );
            if (tmp != null) {
                color = tmp;
                textField.setForeground(color);
                updateExpression();
            }
        }
    }

    /**
     * Écoute les changements du champ contenant l'expression.
     *
     * @author Valentin DOREAU
     */
    private class ExpressionListener implements DocumentListener {
        /**
         * Écoute les évènements d'insertion.
         *
         * @param documentEvent inutilisé
         * @see #updateExpression()
         */
        @Override
        public void insertUpdate(final DocumentEvent documentEvent) {
            updateExpression();
        }

        /**
         * Écoute les évènements de suppression.
         *
         * @param documentEvent inutilisé
         * @see #updateExpression()
         */
        @Override
        public void removeUpdate(final DocumentEvent documentEvent) {
            updateExpression();
        }

        /**
         * Écoute les évènements de changements (coller par exemple).
         *
         * @param documentEvent inutilisé
         * @see #updateExpression()
         */
        @Override
        public void changedUpdate(final DocumentEvent documentEvent) {
            updateExpression();
        }
    }
}
