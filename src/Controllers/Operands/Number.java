package Controllers.Operands;

/**
 * Représente un nombre dans une expression mathématique.
 *
 * @author Valentin DOREAU
 */
public class Number extends Operand {
    /**
     * Valeur du nombre.
     */
    private final double value;

    /**
     * Construit un nombre à partir d'un double.
     *
     * @param value Valeur du nombre
     */
    public Number(double value) {
        this.value = value;
    }

    /**
     * Construit un nombre à partir d'un String.
     *
     * @param value Valeur du nombre
     * @throws NumberFormatException Si le String n'est pas convertible en double
     */
    public Number(String value) throws NumberFormatException {
        this.value = Double.parseDouble(value);
    }

    /**
     * "Calcule" la valeur du nombre (se retourne lui-même).
     *
     * @param x Valeur de l'inconnue dans l'expression
     * @return Lui-même
     */
    @Override
    public Number calculate(final double x) {
        return this;
    }

    /**
     * Converti le nombre en double
     *
     * @return Le double représentant la valeur du nombre
     */
    public double toDouble() {
        return value;
    }

    /**
     * Converti ce nombre en String.
     *
     * @return Ce nombre avec 2 décimales
     */
    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    /**
     * Affiche le nombre à une position donnée avec deux décimales.
     *
     * @param x abscisse du nombre
     * @param y ordonnée du nombre
     */
    @Override
    public void debug(int x, int y) {
        System.out.printf("\033[%d;%dH%.2f", y, x, value);
    }
}
