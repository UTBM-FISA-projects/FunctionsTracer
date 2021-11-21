package Controllers.Operators;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente une division dans une expression.
 *
 * @author Valentin DOREAU
 */
public class Division extends Operator {
    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException En cas de division par zéro
     */
    @Override
    public Number calculate(final Operand A, final Operand B, final double x) throws ArithmeticException {
        double a = A.calculate(x).toDouble();
        double b = B.calculate(x).toDouble();

        if (b == 0) {
            throw new ArithmeticException("Division par zéro");
        }

        return new Number(a / b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "/";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public byte precedence() {
        return 2;
    }
}
