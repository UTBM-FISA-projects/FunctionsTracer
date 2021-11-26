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


        // Graph spinners
        JPanel input = new JPanel();
        input.add(new JLabel("xmin"));
        input.add(new ValueTableSpinner());
        input.add(new JLabel("xmax"));
        input.add(new ValueTableSpinner());
        input.add(new JLabel("ymin"));
        input.add(new ValueTableSpinner());
        input.add(new JLabel("ymax"));
        input.add(new ValueTableSpinner());

        // Graph + Spinner
        Graph graph = new Graph();
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
