package Controllers.Operators;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente l'opérateur modulo.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Modulo_(mathematics)">Modulo</a>
 */
public class Modulo extends Operator {
    /**
     * {@inheritDoc}
     */
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) {
        final double a = A.calculate(x).toDouble();
        final double b = B.calculate(x).toDouble();

        return new Number(a % b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "%";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte precedence() {
        return 2;
    }
}
