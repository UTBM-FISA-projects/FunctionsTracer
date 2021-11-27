package Views.ValueTable;

import Controllers.Operands.Expression;
import Views.MainFrame;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * Représente la fenêtre tableau de valeurs pour une expression.
 *
 * @author Kilian GOËTZ
 * @author Valentin DOREAU
 */
public class ValueTableFrame extends JFrame {
    /**
     * Point de départ du tableau de valeurs.
     */
    private final ValueTableSpinner xmin;
    /**
     * Point d'arrivée du tableau de valeurs.
     */
    private final ValueTableSpinner xmax;
    /**
     * Pas entre chaque point.
     */
    private final ValueTableSpinner step;

    /**
     * Créé un tableau de valeurs pour l'expression donnée.
     *
     * @param expression Expression à traiter
     */
    public ValueTableFrame(final Expression expression) {
        setIconImage(new ImageIcon(MainFrame.class.getResource("logoNoir.png")).getImage());
        setTitle(String.format("Valeurs de %s", expression));
        setMinimumSize(new Dimension(320, 360));
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        //
        // Table
        //
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTable table = new JTable(new ValueTableModel(expression));
        final JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setOpaque(false);
        header.setBackground(new Color(63, 122, 237));
        header.setForeground(new Color(255, 255, 255));

        panel.add(header, BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);

        // xmin
        JPanel inputs = new JPanel();
        inputs.add(new JLabel("xmin"));
        xmin = new ValueTableSpinner();
        xmin.addChangeListener(changeEvent -> {
            table.revalidate();
            table.repaint();
        });
        inputs.add(xmin);

        // xmax
        inputs.add(new JLabel("xmax"));
        xmax = new ValueTableSpinner();
        xmax.setValue(10d);
        xmax.addChangeListener(changeEvent -> {
            table.revalidate();
            table.repaint();
        });
        inputs.add(xmax);

        // pas
        inputs.add(new JLabel("pas"));
        step = new ValueTableSpinner();
        step.addChangeListener(changeEvent -> {
            table.revalidate();
            table.repaint();
        });
        inputs.add(step);

        // scrollPane
        JScrollPane scroll = new JScrollPane(
            panel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );
        mainPanel.add(scroll, BorderLayout.CENTER);
        mainPanel.add(inputs, BorderLayout.NORTH);
        add(mainPanel);
        setVisible(true);
    }

    /**
     * Model de données pour le tableau de valeurs.
     *
     * @author Valentin DOREAU
     */
    private class ValueTableModel implements TableModel {
        /**
         * Expression à calculer
         */
        private final Expression expression;

        /**
         * Créer un model de données associé à l'expression donnée.
         *
         * @param expr Expression à calculer
         */
        public ValueTableModel(final Expression expr) {
            expression = expr;
        }

        /**
         * Calcul le nombre de lignes du tableau.
         *
         * @return Le nombre de lignes du tableau
         */
        @Override
        public int getRowCount() {
            final double stepd = (Double) step.getValue();
            final double mind = (Double) xmin.getValue();
            final double maxd = (Double) xmax.getValue();

            try {
                return (int) (Math.abs(maxd - mind) / Math.abs(stepd) + 1);
            } catch (ArithmeticException ignored) {
                return 0;
            }
        }

        /**
         * Retourne le nombre de colonnes. Toujours deux : X et Y.
         *
         * @return 2
         */
        @Override
        public int getColumnCount() {
            return 2;
        }

        /**
         * Retourne le nom de chaque colonne.<br>
         * "abscisse (x)" pour la colonne 0.<br>
         * "ordonnée (y)" pour la colonne 1.
         *
         * @param col Le numéro de la colonne
         * @return Le nom de la colonne correspondante au numéro
         */
        @Override
        public String getColumnName(final int col) {
            return new String[]{"abscisse (x)", "ordonnée (y)"}[col];
        }

        /**
         * Retourne la classe de la colonne, toujours Double.
         *
         * @param col Indice de la colonne à récupérer
         * @return Double
         */
        @Override
        public Class<?> getColumnClass(final int col) {
            return Double.class;
        }

        /**
         * Retourne toujours false, aucune cellule n'est éditable.
         *
         * @param i  Numéro de ligne
         * @param i1 Numéro de colonne
         * @return Toujours false
         */
        @Override
        public boolean isCellEditable(final int i, final int i1) {
            return false;
        }

        /**
         * Retourne la valeur à affiche à la colonne {@code col} et à la ligne {@code row}.
         *
         * @param row Ligne à calculer
         * @param col Colonne à calculer
         * @return La valeur à afficher
         */
        @Override
        public Object getValueAt(final int row, final int col) {
            final double start = (Double) xmin.getValue();
            final double stop = (Double) xmax.getValue();
            final double stepd = Math.abs((Double) step.getValue()) * (start < stop ? 1 : -1);
            final double xAtRow = start + row * stepd;

            if (col == 1) {
                try {
                    return expression.calculate(xAtRow).toDouble();
                } catch (ArithmeticException e) {
                    return Double.NaN;
                }
            }

            return xAtRow;
        }

        /**
         * Ne fais rien, aucune valeur ne peux être définie.
         *
         * @param o  inutilisé
         * @param i  inutilisé
         * @param i1 inutilisé
         */
        @Override
        public void setValueAt(final Object o, final int i, final int i1) {
        }

        /**
         * Ne fais rien, aucun listener ne peux être défini.
         *
         * @param tableModelListener inutilisé
         */
        @Override
        public void addTableModelListener(final TableModelListener tableModelListener) {
        }

        /**
         * Ne fais rien, aucun listener ne peux est ni défini ni retirer.
         *
         * @param tableModelListener inutilisé
         */
        @Override
        public void removeTableModelListener(final TableModelListener tableModelListener) {
        }
    }
}
