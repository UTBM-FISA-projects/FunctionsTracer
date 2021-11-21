package Controllers.Operands;

/**
 * Représente un opérande dans une expression mathématique.
 *
 * @author Valentin DOREAU
 */
abstract public class Operand {
    /**
     * Calcule la valeur de l'opérande.
     *
     * @param x Valeur de l'inconnue dans l'expression
     * @return La valeur de l'opérande
     */
    abstract public Number calculate(final double x);

    /**
     * Affiche l'expression pour du debug.
     *
     * @param x abscisse
     * @param y ordonnée
     */
    abstract public void debug(int x, int y);
}
