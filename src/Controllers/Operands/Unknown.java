package Controllers.Operands;

/**
 * Représente une inconnue dans une expression mathématique.
 *
 * @author Valentin DOREAU
 */
public class Unknown extends Operand {
    /**
     * "Calcule" la valeur de l'inconnue (retourne le X passé en paramètre).
     *
     * @param x Valeur de l'inconnue dans l'expression
     * @return La valeur de l'inconnue
     */
    @Override
    public Number calculate(final double x) {
        return new Number(x);
    }

    /**
     * Convertie l'inconnue en String.<br>
     *
     * @return X
     */
    @Override
    public String toString() {
        return "x";
    }

    /**
     * Affiche l'inconnue aux coordonnées données.
     *
     * @param x abscisse
     * @param y ordonnée
     */
    @Override
    public void debug(int x, int y) {
        System.out.printf("\033[%d;%dHx", y, x);
    }
}
