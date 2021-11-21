package Controllers.Operators.Functions;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente la racine cubique.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Cube_root">Cube root</a>
 */
public class CubeRoot extends Function {

    /**
     * {@inheritDoc}
     */
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) {
        final double a = A.calculate(x).toDouble();

        return new Number(Math.cbrt(a));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "cbrt";
    }
}
