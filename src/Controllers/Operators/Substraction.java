package Controllers.Operators;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente une soustraction dans une expression.
 *
 * @author Valentin DOREAU
 */
public class Substraction extends Operator {
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) {
        final double a = A.calculate(x).toDouble();
        final double b = B.calculate(x).toDouble();

        return new Number(a - b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "-";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte precedence() {
        return 1;
    }
}
