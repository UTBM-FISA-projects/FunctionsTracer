package Views;

import Views.MenuBar.MenuBar;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Functions Tracer");

        // window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setIconImage(new ImageIcon("resources/logoNoir.png").getImage());
        setMinimumSize(new Dimension(640, 360));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // expression list
        ExpressionList expressionList = new ExpressionList();
        JScrollPane scrollPane = new JScrollPane(
            expressionList,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        mainPanel.add(scrollPane, BorderLayout.LINE_START);

        // graph
        Graph graph = new Graph(-10, 10, -10, 10);
        mainPanel.add(graph, BorderLayout.CENTER);

        this.setContentPane(mainPanel);
        setJMenuBar(new MenuBar(expressionList));
    }

}