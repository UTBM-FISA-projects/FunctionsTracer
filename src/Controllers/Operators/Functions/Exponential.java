package Controllers.Operators.Functions;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente la fonction exponentielle.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Exponential_function">Exponential function</a>
 */
public class Exponential extends Function {
    /**
     * {@inheritDoc}
     */
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) {
        final double a = A.calculate(x).toDouble();

        return new Number(Math.exp(a));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "e";
    }
}
