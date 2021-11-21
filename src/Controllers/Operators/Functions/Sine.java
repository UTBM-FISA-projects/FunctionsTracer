package Controllers.Operators.Functions;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente la fonction sinus.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Sine_and_cosine">Sine</a>
 */
public class Sine extends Function {
    /**
     * {@inheritDoc}
     */
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) {
        double a = A.calculate(x).toDouble();

        return new Number(Math.sin(a));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "sin";
    }
}
