package Views.ValueTable;

import javax.swing.*;
import java.awt.*;

/**
 * Représente JSpinner nécessaire pour le boutton de la table des valeurs
 *
 * @author Kilian GOËTZ
 */
public class ValueTableSpinner extends JSpinner {
    /**
     * Créer les JSpinner nécessaire pour le boutton de la table des valeurs
     *
     * @author Kilian GOËTZ
     */
    public ValueTableSpinner() {
        setPreferredSize(new Dimension(60, 30));
        setModel(new SpinnerNumberModel(1d, -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
    }
}
