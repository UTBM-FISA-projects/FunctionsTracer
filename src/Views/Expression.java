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
    private int index = -1;
    private Color color = Color.RED;

    public Expression(Graph graph, ExpressionList.ActionDelete actionDelete) {
        super();

        this.graph = graph;

        setLayout(new FlowLayout());

        //Bouton Choix couleur
        Image imgdel = getToolkit().getImage("resources/color.png");
        Image newimgdel = imgdel.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        Icon icondel = new ImageIcon(newimgdel);
        JButton delButton = new ExpressionButtons(icondel);
        delButton.addActionListener(new ActionColor());
        add(delButton);

        //Textfield
        textField = new TextField();
        textField.setForeground(color);
        textField.getDocument().addDocumentListener(new ExpressionListener());
        add(textField);

        //Bouton Supprimer
        Image imgColor = getToolkit().getImage("resources/trash.png");
        Image newimgColor = imgColor.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        Icon iconColor = new ImageIcon(newimgColor);
        JButton colorButton = new ExpressionButtons(iconColor);
        actionDelete.setElement(this);
        colorButton.addActionListener(actionDelete);
        add(colorButton);


        //Bouton Tableau
        Image imgtab = getToolkit().getImage("resources/table.png");
        Image newimgtab = imgtab.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        Icon icontab = new ImageIcon(newimgtab);
        JButton tabButton = new ExpressionButtons(icontab);
        tabButton.addActionListener(new ActionTable());
        add(tabButton);

    }

    public String getExpression() {
        return textField.getText();
    }

    private void updateExpression() {
        try {
            Parser parser = new Parser(textField.getText());
            Controllers.Operands.Expression expr = parser.parse();
            index = graph.addExpression(index, expr, color);
        } catch (MismatchParenthesisException | MalformedExpressionException | EmptyStackException ignored) {
            graph.removeExpression(index);
            index = -1;
        }
    }

    private class ActionTable extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new ValueTableFrame(new Parser(textField.getText()).parse());
            } catch (MismatchParenthesisException | MalformedExpressionException ignored) {
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
