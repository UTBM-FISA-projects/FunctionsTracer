package Controllers.Operators.Functions;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente la fonction valeur absolue.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Absolute_value">Absolute value</a>
 */
public class Absolute extends Function {
    /**
     * {@inheritDoc}
     */
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) {
        double a = A.calculate(x).toDouble();

        return new Number(Math.abs(a));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "abs";
    }
}
