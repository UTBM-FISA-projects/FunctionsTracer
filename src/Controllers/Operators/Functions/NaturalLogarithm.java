package Controllers.Operators.Functions;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente le logarithme népérien ou logarithme naturel.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Natural_logarithm">Natural logarithm</a>
 */
public class NaturalLogarithm extends Function {
    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException Si A est inférieur ou égale à 0
     */
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) throws ArithmeticException {
        final double a = A.calculate(x).toDouble();

        if (a <= 0) {
            throw new ArithmeticException("ln n'est défini que sur les positifs strict.");
        }

        return new Number(Math.log(a));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "ln";
    }
}
