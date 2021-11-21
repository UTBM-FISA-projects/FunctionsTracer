package Controllers.Operators.Functions;

import Controllers.Operators.Operator;
import Controllers.Operators.Properties.Arity;

/**
 * Représente une fonction dans une expression mathématique.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/List_of_mathematical_functions">List of mathematical functions</a>
 */
abstract public class Function extends Operator {
    /**
     * Retourne la priorité de cette fonction.
     *
     * @return La priorité de cette fonction
     * @see <a href="https://en.wikipedia.org/wiki/Order_of_operations">Order of operations</a>
     */
    @Override
    public final byte precedence() {
        return 3;
    }

    /**
     * Définis l'arité de la fonction.
     *
     * @return L'arité de la fonction.
     * @see Arity
     */
    @Override
    public Arity arity() {
        return Arity.UNARY;
    }
}
