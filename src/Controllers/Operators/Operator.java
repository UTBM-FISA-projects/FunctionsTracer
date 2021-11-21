package Controllers.Operators;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;
import Controllers.Operators.Properties.Arity;
import Controllers.Operators.Properties.Associativity;

/**
 * Représente un opérateur dans une expression mathématique.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Glossary_of_mathematical_symbols#Arithmetic_operators">List of
 * mathematical arithemtic symbols</a>
 */
abstract public class Operator {

    /**
     * Défini l'associativité de l'opérateur.
     *
     * @return L'associativité de l'opérateur
     */
    public Associativity associativity() {
        return Associativity.LEFT_ASSOCIATIVE;
    }

    /**
     * Détermine si l'opérateur est associatif à gauche.
     *
     * @return true si l'opérateur est associatif à gauche, false sinon
     */
    public final boolean isLeftAssociative() {
        return associativity() == Associativity.LEFT_ASSOCIATIVE;
    }

    /**
     * Applique l'opérateur sur les deux ou un seul opérande selon son arité.
     *
     * @param A Premier opérande
     * @param B Second opérande
     * @param x Valeur de l'inconnue dans l'expression
     * @return Le nombre résultant de l'opération
     */
    abstract public Number calculate(final Operand A, final Operand B, final double x);

    /**
     * Défini le symbole de l'opérateur.
     *
     * @return Le symbole de l'opérateur
     */
    abstract public String symbol();

    /**
     * Retourne la priorité de cet opérateur.
     *
     * @return La priorité de cet opérateur
     * @see <a href="https://en.wikipedia.org/wiki/Order_of_operations">Order of operations</a>
     */
    abstract public byte precedence();

    /**
     * Définis l'arité de l'opérateur.
     *
     * @return L'arité de l'opérateur.
     * @see Arity
     */
    public Arity arity() {
        return Arity.BINARY;
    }

    /**
     * Convertis l'opérateur en String
     *
     * @return L'opérateur sous forme de String
     */
    @Override
    public String toString() {
        return symbol();
    }
}
