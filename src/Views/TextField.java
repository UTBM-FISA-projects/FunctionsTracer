package Views;

import javax.swing.*;
import java.awt.*;

/**
 * Représente le champ texte ou l'on rentre les expressions.
 *
 * @author Kilian GOËTZ
 */
public class TextField extends JTextField {

    /**
     * Créer le champ texte pour rentrer une expression
     */
    public TextField() {
        setFont(new Font("Latex", Font.PLAIN, 20));
        setPreferredSize(new Dimension(200, 30));
    }
}
