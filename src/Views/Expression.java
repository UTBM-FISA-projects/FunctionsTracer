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

public class Expression extends JPanel {

    private final TextField textField;
    private final Graph graph;
    private Color color = Color.RED;

    public Expression(Graph graph, ExpressionList.ActionDelete actionDelete) {
        super();

        this.graph = graph;

        setLayout(new FlowLayout());

        //Bouton Choix couleur
        JButton delButton = new ExpressionButtons(getClass().getResource("color.png"), "Couleur de la courbe");
        delButton.addActionListener(new ActionColor());
        add(delButton);

        //Textfield
        textField = new TextField();
        textField.setForeground(color);
        textField.getDocument().addDocumentListener(new ExpressionListener());
        add(textField);

        //Bouton Supprimer
        JButton colorButton = new ExpressionButtons(getClass().getResource("trash.png"), "Supprimer l'expression");
        actionDelete.setElement(this);
        colorButton.addActionListener(actionDelete);
        add(colorButton);


        //Bouton Tableau
        JButton tabButton = new ExpressionButtons(getClass().getResource("table.png"), "Tableau de valeurs");
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

    private void updateExpression() {
        try {
            Parser parser = new Parser(textField.getText());
            final Controllers.Operands.Expression expr = parser.parse();
            graph.addExpression(hashCode(), expr, color);
        } catch (MismatchParenthesisException | MalformedExpressionException | EmptyStackException ignored) {
            graph.removeExpression(hashCode());
        }
    }

    private class ActionTable extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new ValueTableFrame(new Parser(textField.getText()).parse());
            } catch (MismatchParenthesisException | MalformedExpressionException | EmptyStackException ignored) {
            }
        }
    }

    private class ActionColor extends AbstractAction {
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

    private class ExpressionListener implements DocumentListener {
        @Override

        public void insertUpdate(final DocumentEvent documentEvent) {
            updateExpression();
        }

        @Override
        public void removeUpdate(final DocumentEvent documentEvent) {
            updateExpression();
        }

        @Override
        public void changedUpdate(final DocumentEvent documentEvent) {
            updateExpression();

        }
    }
}
