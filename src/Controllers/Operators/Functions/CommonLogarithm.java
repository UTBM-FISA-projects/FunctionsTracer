package Controllers.Operators.Functions;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente le logarithme.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Common_logarithm">Common logarithm</a>
 */
public class CommonLogarithm extends Function {
    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException Si A est inférieur ou égale à 0
     */
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) throws ArithmeticException {
        final double a = A.calculate(x).toDouble();

        if (a <= 0) {
            throw new ArithmeticException("log n'est défini que sur les positifs strict.");
        }

        return new Number(Math.log10(a));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "log";
    }
}
