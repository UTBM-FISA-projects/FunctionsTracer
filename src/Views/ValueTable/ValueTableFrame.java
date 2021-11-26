package Views.ValueTable;

import Controllers.Operands.Expression;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import java.awt.*;

/**
 * @author Kilian GOËTZ
 * @author Valentin DOREAU
 */
public class ValueTableFrame extends JFrame {
    private final ValueTableSpinner xmin;
    private final ValueTableSpinner xmax;
    private final ValueTableSpinner step;

    public ValueTableFrame(final Expression expression) {
        setIconImage(new ImageIcon("resources/logoNoir.png").getImage());
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

        //
        // Créer les 3 JSpinner
        //
        JPanel inputs = new JPanel();
        inputs.add(new JLabel("xmin"));
        xmin = new ValueTableSpinner();
        xmin.addChangeListener(changeEvent -> table.revalidate());
        inputs.add(xmin);

        inputs.add(new JLabel("xmax"));
        xmax = new ValueTableSpinner();
        xmax.setValue(10d);
        xmax.addChangeListener(changeEvent -> table.revalidate());
        inputs.add(xmax);

        inputs.add(new JLabel("pas"));
        step = new ValueTableSpinner();
        step.addChangeListener(changeEvent -> table.revalidate());
        inputs.add(step);

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

    private class ValueTableModel implements TableModel {
        private final Expression expression;

        public ValueTableModel(final Expression expr) {
            expression = expr;
        }

        @Override
        public int getRowCount() {
            final double stepi = (Double) step.getValue();
            final double mini = (Double) xmin.getValue();
            final double maxi = (Double) xmax.getValue();

            try {
                return (int) (Math.abs(maxi - mini) / stepi + 1);
            } catch (ArithmeticException ignored) {
                return 0;
            }
        }

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public String getColumnName(final int i) {
            return new String[]{"abscisse (x)", "ordonnée (y)"}[i];
        }

        @Override
        public Class<?> getColumnClass(final int i) {
            return Double.class;
        }

        @Override
        public boolean isCellEditable(final int i, final int i1) {
            return false;
        }

        @Override
        public Object getValueAt(final int row, final int col) {
            final double xAtRow = ((Double) xmin.getValue()) + row * ((Double) step.getValue());

            if (col == 1) {
                try {
                    return expression.calculate(xAtRow).toDouble();
                } catch (ArithmeticException e) {
                    return Double.NaN;
                }
            }

            return xAtRow;
        }

        @Override
        public void setValueAt(final Object o, final int i, final int i1) {
        }

        @Override
        public void addTableModelListener(final TableModelListener tableModelListener) {
        }

        @Override
        public void removeTableModelListener(final TableModelListener tableModelListener) {
        }
    }
}
