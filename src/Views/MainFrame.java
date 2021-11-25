package Views;

import Views.MenuBar.MenuBar;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final Image img;
    private final JPanel mainPanel;

    public MainFrame() {

        super("Functions Tracer");

        img = new ImageIcon("resources/logoNoir.png").getImage();
        setIconImage(img);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(640, 360));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        ExpressionList expressionList = new ExpressionList();

        JScrollPane scrollPane = new JScrollPane(
                expressionList,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(this.getSize());
        mainPanel = new JPanel();
        mainPanel.add(scrollPane);
        mainPanel.add(new Canvas());
        this.setContentPane(mainPanel);

        setJMenuBar(new MenuBar(expressionList));
        setVisible(true);

    }

}
