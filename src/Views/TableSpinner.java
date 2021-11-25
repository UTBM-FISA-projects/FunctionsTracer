package Views;

import javax.swing.*;
import java.awt.*;

/**
 * Représente JSpinner nécessaire pour le boutton de la table des valeurs
 *
 * @author Kilian GOËTZ
 */
public class TableSpinner extends JSpinner {
    /**
     * Créer les JSpinner nécessaire pour le boutton de la table des valeurs
     *
     * @author Kilian GOËTZ
     */
    public TableSpinner() {
        setPreferredSize(new Dimension(60, 30));
        setModel(new SpinnerNumberModel());
    }
}
