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
     * Créé les différents boutons pour les expressions et celui pour ajouter des expressions
     *
     * @param iconPath Chemin de l'icon
     * @author Kilian
     */
    public ExpressionButtons(String iconPath) {
        this(iconPath, 20, 20);
    }

    /**
     * Créé un bouton d'expression.
     *
     * @param iconPath   Chemin de l'icon
     * @param iconWidth  Largeur de l'icon
     * @param iconHeight Hauteur de l'icon
     */
    public ExpressionButtons(String iconPath, int iconWidth, int iconHeight) {
        super();

        final Icon icon = new ImageIcon(
            getToolkit().getImage(iconPath).getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH)
        );
        setIcon(icon);

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }
}
