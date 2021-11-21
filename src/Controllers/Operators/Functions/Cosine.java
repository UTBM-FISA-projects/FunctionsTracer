package Controllers.Operators.Functions;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente la fonction cosinus.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Sine_and_cosine">Cosine</a>
 */
public class Cosine extends Function {
    /**
     * {@inheritDoc}
     */
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) {
        double a = A.calculate(x).toDouble();

        return new Number(Math.cos(a));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "cos";
    }
}
