package Controllers.Operators;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente une addition dans une expression.
 *
 * @author Valentin DOREAU
 */
public class Addition extends Operator {
    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "+";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte precedence() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Number calculate(Operand A, Operand B, final double x) {
        double a = A.calculate(x).toDouble();
        double b = B.calculate(x).toDouble();

        return new Number(a + b);
    }
}
