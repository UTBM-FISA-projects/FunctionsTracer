package Views;

import Views.MenuBar.MenuBar;
import Views.ValueTable.ValueTableSpinner;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Functions Tracer");

        // window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setIconImage(new ImageIcon(getClass().getResource("logoNoir.png")).getImage());
        setMinimumSize(new Dimension(640, 360));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Graph
        final Graph graph = new Graph();

        // Graph spinners
        final JPanel input = new JPanel();
        input.add(new JLabel("xmin"));
        final ValueTableSpinner xmin = new ValueTableSpinner(-10);
        xmin.addChangeListener(ignored -> graph.setXMin((Double) xmin.getValue()));
        input.add(xmin);

        input.add(new JLabel("xmax"));
        final ValueTableSpinner xmax = new ValueTableSpinner(10);
        xmax.addChangeListener(ignored -> graph.setXMax((Double) xmax.getValue()));
        input.add(xmax);

        input.add(new JLabel("ymin"));
        final ValueTableSpinner ymin = new ValueTableSpinner(-10);
        ymin.addChangeListener(ignored -> graph.setYMin((Double) ymin.getValue()));
        input.add(ymin);

        input.add(new JLabel("ymax"));
        final ValueTableSpinner ymax = new ValueTableSpinner(10);
        ymax.addChangeListener(ignored -> graph.setYMax((Double) ymax.getValue()));
        input.add(ymax);

        // addition Graph + Spinner
        JPanel graphSpinner = new JPanel();
        graphSpinner.setLayout(new BorderLayout());
        graphSpinner.add(graph, BorderLayout.CENTER);
        graphSpinner.add(input, BorderLayout.NORTH);
        mainPanel.add(graphSpinner, BorderLayout.CENTER);

        // expression list
        ExpressionList expressionList = new ExpressionList(graph);
        JScrollPane scrollPane = new JScrollPane(
            expressionList,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        mainPanel.add(scrollPane, BorderLayout.LINE_START);

        this.setContentPane(mainPanel);
        setJMenuBar(new MenuBar(expressionList));
    }

}
