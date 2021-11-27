package Controllers.Operands;

import Controllers.Operators.Functions.Function;
import Controllers.Operators.Operator;
import Controllers.Operators.Properties.Arity;

/**
 * Représente une expression mathématique sous forme d'arbre.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Abstract_syntax_tree">Abstract syntax tree</a>
 * @see <a href="https://en.wikipedia.org/wiki/Binary_expression_tree">Binary expression tree</a>
 */
public class Expression extends Operand {
    /**
     * Premier opérande de l'expression
     */
    private final Operand firstOperand;
    /**
     * Second opérande de l'expression
     */
    private final Operand secondOperand;
    /**
     * Opérateur de l'expression (peut être une fonction)
     */
    private final Operator operator;

    /**
     * Construit une expression mathématique composée de deux opérandes et un opérateur.<br>
     * Si {@code reverse} est à true, {@code firstOperand} est le second et inversement
     *
     * @param firstOperand  Premier opérande
     * @param secondOperand Second opérande
     * @param operator      Opérateur ou fonction
     * @param reverse       Inverse les deux opérandes
     */
    public Expression(Operand firstOperand, Operand secondOperand, Operator operator, boolean reverse) {
        this.firstOperand = reverse ? secondOperand : firstOperand;
        this.secondOperand = reverse ? firstOperand : secondOperand;
        this.operator = operator;
    }

    /**
     * Construit une expression mathématique composée d'un seul opérande et d'un opérateur.
     *
     * @param firstOperand Premier et unique opérande
     * @param operator     Opérateur ou fonction
     * @throws IllegalArgumentException Si l'opérateur n'est pas unaire
     * @see Arity
     */
    public Expression(Operand firstOperand, Operator operator) throws IllegalArgumentException {
        if (operator.arity() != Arity.UNARY) {
            throw new IllegalArgumentException("Operator arity doesn't correspond to the given number of operand (1).");
        }

        this.firstOperand = firstOperand;
        this.secondOperand = null;
        this.operator = operator;
    }

    /**
     * Calcule la valeur de l'expression pour le {@code x} donné.
     *
     * @param x Valeur de l'inconnue
     * @return La valeur de l'expression
     */
    @Override
    public Number calculate(final double x) {
        if (operator != null) {
            return operator.calculate(firstOperand, secondOperand, x);
        } else {
            return firstOperand.calculate(x);
        }
    }

    /**
     * Calcule la profondeur de l'arbre.
     *
     * @return profondeur de l'arbre
     */
    public int depth() {
        int a = 0, b = 0;

        if (firstOperand != null && firstOperand.getClass() == Expression.class) {
            a = ((Expression) firstOperand).depth();
        }

        if (secondOperand != null && secondOperand.getClass() == Expression.class) {
            b = ((Expression) secondOperand).depth();
        }

        return Math.max(a + 1, b + 1);
    }

    /**
     * Affiche l'expression sous forme de string.
     *
     * @return L'expression en notation infix
     */
    @Override
    public String toString() {
        final String out;

        if (operator == null) {
            return firstOperand.toString();
        }

        if (operator.arity() == Arity.UNARY) {
            out = String.format("%s(%s)", operator, firstOperand);
        } else if (operator.arity() == Arity.BINARY && operator instanceof Function) {
            out = String.format("%s(%s, %s)", operator, firstOperand, secondOperand);
        } else {
            out = String.format("(%s %s %s)", firstOperand, operator, secondOperand);
        }

        return out;
    }

    /**
     * Affiche l'expression sous forme d'arbre dans le terminal.<br>
     * Experimental (peut ne pas complètement fonctionner), debugging purposes only.
     * <p>
     * Ne fonctionne pas dans le terminal des IDEs IntelliJ à cause des codes ANSI.
     *
     * @param x abscisse du premier élément
     * @param y ordonnée du premier élément
     * @see <a href="https://en.wikipedia.org/wiki/ANSI_escape_code#CSI_(Control_Sequence_Introducer)_sequences">
     * ANSI escape code
     * </a>
     */
    @Override
    public void debug(int x, int y) {
        System.out.printf("\033[%d;%dH%s", y, x, operator);

        final int depth = depth() * 2;

        if (firstOperand != null) {
            System.out.printf("\033[%d;%dH/%s/", y + 1, x - depth - 2, "-".repeat(depth));
            firstOperand.debug(x - depth - 3, y + 2);
        }

        if (secondOperand != null) {
            System.out.printf("\033[%d;%dH\\%s\\", y + 1, x + 1, "-".repeat(depth));
            secondOperand.debug(x + depth + 3, y + 2);
        }

        System.out.print("\033[100;100H");
    }
}
