package Controllers.Operators;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;
import Controllers.Operators.Properties.Associativity;

/**
 * Représente une puissance dans une expression.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Exponentiation">Exponentiation</a>
 */
public class Exponentiation extends Operator {
    /**
     * {@inheritDoc}
     */
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) {
        double a = A.calculate(x).toDouble();
        double b = B.calculate(x).toDouble();

        // Puissance est définie sur R+* pour les puissances réels
        if (b % 1 != 0 && a <= 0) {
            throw new ArithmeticException();
        }

        return new Number(Math.pow(a, b));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "^";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Associativity associativity() {
        return Associativity.RIGHT_ASSOCIATIVE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte precedence() {
        return 3;
    }
}
