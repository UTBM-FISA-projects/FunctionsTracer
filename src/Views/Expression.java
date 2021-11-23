package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Expression extends JPanel implements ActionListener{

    private TextField textField;

    public Expression(){
        setLayout(new FlowLayout());
        textField = new TextField();
        add(textField);
        JButton delButton = new JButton("Supprimer");
        delButton.addActionListener(this);
        add(delButton);
    }

    public String getExpression(){

        return textField.getText();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        revalidate();
    }
}
