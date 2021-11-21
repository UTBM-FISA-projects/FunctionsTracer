package Controllers.Operators.Functions;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;
import Controllers.Operators.Properties.Arity;

/**
 * Représente la fonction maximum.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Maxima_and_minima">Maxima</a>
 */
public class Maxima extends Function {
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) {
        double a = A.calculate(x).toDouble();
        double b = B.calculate(x).toDouble();

        return new Number(Math.max(a, b));
    }

    @Override
    public String symbol() {
        return "max";
    }

    @Override
    public Arity arity() {
        return Arity.BINARY;
    }
}
