package Controllers.Operators.Functions;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente la fonction tangente.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Trigonometric_functions">Trigonometric functions</a>
 */
public class Tangent extends Function {
    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException Si la valeur donnée n'est pas définie
     */
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) throws ArithmeticException {
        final double a = A.calculate(x).toDouble();

        // tangente n'est pas définie quand
        // x = PI/2 + k*PI
        if (((x - Math.PI) / 2 / Math.PI) % 1 == 0) {
            throw new ArithmeticException("tan n'est pas définie sur PI/2 + k*PI");
        }

        return new Number(Math.tan(a));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "tan";
    }
}
