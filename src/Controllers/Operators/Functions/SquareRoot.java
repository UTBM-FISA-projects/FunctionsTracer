package Controllers.Operators.Functions;

import Controllers.Operands.Number;
import Controllers.Operands.Operand;

/**
 * Représente la fonction racine carrée.
 *
 * @author Valentin DOREAU
 */
public class SquareRoot extends Function {
    /**
     * {@inheritDoc}
     *
     * @throws ArithmeticException En cas de paramètre inférieur à 0
     */
    @Override
    public Number calculate(Operand A, Operand B, final double x) throws ArithmeticException {
        double a = A.calculate(x).toDouble();

        if (a < 0) {
            throw new ArithmeticException("Racine carrée n'est définie que pour les nombres positifs strict.");
        }

        return new Number(Math.sqrt(a));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String symbol() {
        return "sqrt";
    }
}
