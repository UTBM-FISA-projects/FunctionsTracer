package Views;

import Controllers.Parser.Parser;
import Exceptions.MalformedExpressionException;
import Exceptions.MismatchParenthesisException;

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

    public Expression(Graph graph) {
        super();

        this.graph = graph;

        setLayout(new FlowLayout());
        textField = new TextField();

        //Bouton Choix couleur
        Image imgdel = getToolkit().getImage("resources/color.png");
        Image newimgdel = imgdel.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        Icon icondel = new ImageIcon(newimgdel);
        JButton delButton = new ExpressionButtons(icondel);
        delButton.addActionListener(new ActionColor());
        add(delButton);

        //Textfield
        textField.getDocument().addDocumentListener(new ExpressionListener());
        add(textField);

        //Bouton Supprimer
        Image imgColor = getToolkit().getImage("resources/trash.png");
        Image newimgColor = imgColor.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        Icon iconColor = new ImageIcon(newimgColor);
        JButton colorButton = new ExpressionButtons(iconColor);
        colorButton.addActionListener(new ActionDelete());
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

    private static class ActionColor extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color newColor = JColorChooser.showDialog(
                null,
                "Couleur de la courbe",
                Color.black
            );
        }
    }

    private static class ActionDelete extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private static class ActionTable extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frameTable = new JFrame();
            Image img = new ImageIcon("resources/logoNoir.png").getImage();
            JPanel panel = new JPanel();
            JPanel input = new JPanel();
            JPanel mainPanel = new JPanel();
            panel.setLayout(new BorderLayout());
            mainPanel.setLayout(new BorderLayout());
            String[] titles = {"abscisse (x)", "ordonnée (y)"};
            String[][] data = {{"1", "2"}, {"2", "3"}};

            //Personnalisation de la JTable
            JTable table = new JTable(data, titles);
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
            table.getTableHeader().setOpaque(false);
            table.getTableHeader().setBackground(new Color(63, 122, 237));
            table.getTableHeader().setForeground(new Color(255, 255, 255));

            //Créer les 3 JSpinner
            input.add(new JLabel("xmin"));
            input.add(new TableSpinner());
            input.add(new JLabel("xmax"));
            input.add(new TableSpinner());
            input.add(new JLabel("pas"));
            input.add(new TableSpinner());

            mainPanel.add(input, BorderLayout.NORTH);
            panel.add(table.getTableHeader(), BorderLayout.NORTH);
            panel.add(table, BorderLayout.CENTER);
            mainPanel.add(panel, BorderLayout.CENTER);
            frameTable.add(mainPanel);
            frameTable.setIconImage(img);
            frameTable.setTitle("Valeurs");
            frameTable.setMinimumSize(new Dimension(320, 360));
            frameTable.setVisible(true);
        }
    }

    private class ExpressionListener implements DocumentListener {
        private void updateExpression() {
            try {
                Parser parser = new Parser(textField.getText());
                Controllers.Operands.Expression expr = parser.parse();
                index = graph.addExpression(index, expr);
            } catch (MismatchParenthesisException | MalformedExpressionException | EmptyStackException ignored) {
                graph.removeExpression(index);
                index = -1;
            }
        }

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
