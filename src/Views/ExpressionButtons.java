package Views;

import javax.swing.*;
import java.awt.*;

/**
 * Représente les différents boutons associés aux espressions
 *
 * @author Kilian GOËTZ
 */
public class ExpressionButtons extends JButton {
    /**
     * Créer les différents boutons pour les expressions et celui pour ajouter des expressions
     *
     * @param icon
     * @author Kilian
     */
    public ExpressionButtons(Icon icon) {
        setIcon(icon);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
