package Views.ValueTable;

import javax.swing.*;
import java.awt.*;

/**
 * Représente JSpinner nécessaire pour le button de la table des valeurs.
 *
 * @author Kilian GOËTZ
 */
public class ValueTableSpinner extends JSpinner {
    /**
     * Créé un spinner avec 1 comme valeur par défaut.
     */
    public ValueTableSpinner() {
        this(1d);
    }

    /**
     * Créé les JSpinner nécessaire pour le bouton de la table des valeurs
     *
     * @param defaultValue Valeur par défaut
     */
    public ValueTableSpinner(double defaultValue) {
        setPreferredSize(new Dimension(60, 30));
        setModel(new SpinnerNumberModel(defaultValue, -Double.MAX_VALUE, Double.MAX_VALUE, 0.1));
    }
}
