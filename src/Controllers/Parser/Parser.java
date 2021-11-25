package Controllers.Parser;

import Controllers.Operands.Expression;
import Controllers.Operands.Number;
import Controllers.Operands.Operand;
import Controllers.Operands.Unknown;
import Controllers.Operators.Functions.*;
import Controllers.Operators.Operator;
import Controllers.Operators.Properties.Arity;
import Exceptions.MalformedExpressionException;
import Exceptions.MismatchParenthesisException;

import java.util.*;

/**
 * Représente l'algorithme de la gare de triage de Dijkstra.<br>
 * Permet de transformer une notation infix en notation postfix.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">Shunting yard algorythm</a>
 */
public class Parser {

    /**
     * Liste des opérateurs disponibles pour le parser.
     */
    private final OperatorList operators = new OperatorList();
    /**
     * Liste des fonctions disponibles pour le parser.
     */
    private final OperatorList functions = new OperatorList(new ArrayList<>(List.of(
        new Absolute(),
        new CommonLogarithm(),
        new Cosine(),
        new CubeRoot(),
        new Exponential(),
        new Maxima(),
        new NaturalLogarithm(),
        new Sine(),
        new SquareRoot(),
        new Tangent()
    )));
    /**
     * Liste des opérateurs et fonctions disponibles.
     */
    private final OperatorList all = new OperatorList(operators, functions);

    /**
     * File des tokens représentants l'expression.
     */
    private final Queue<String> tokens = new LinkedList<>();

    /**
     * Pile des opérandes.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">Shunting yard algorythm</a>
     */
    private Stack<Operand> stack;
    /**
     * Pile des opérateurs.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">Shunting yard algorythm</a>
     */
    private Stack<String> opStack;

    /**
     * Créé un parser sur une expression mathématique.
     *
     * @param expression Views.Expression à traiter
     */
    public Parser(final String expression) {
        updateExpression(expression);
    }

    /**
     * Vérifie si un String est un double.
     *
     * @param s String à vérifier
     * @return true si le String est un double, false sinon
     */
    private static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Enregistre une nouvelle fonction pour le parser.
     *
     * @param o Fonction
     */
    public void registerFunction(final Function o) {
        functions.register(o);
        all.register(o);
    }

    /**
     * Enregistre un nouvel opérateur pour le parser.
     *
     * @param o Opérateur
     */
    public void registerOperator(final Operator o) {
        operators.register(o);
        all.register(o);
    }

    /**
     * Met à jour la file de tokens représentant l'expression.
     *
     * @param expression Views.Expression à parser
     */
    public void updateExpression(final String expression) {
        tokenize(
            expression
                .toLowerCase()
                .replace("\s", "")
        );
    }

    /**
     * Transforme un String en file de token.
     *
     * @param expression Views.Expression mathématique à parser
     * @see <a href="https://en.wikipedia.org/wiki/Lexical_analysis#Token">Lexical analysis</a>
     */
    private void tokenize(String expression) {
        char c;
        StringBuilder buffer = null;

        for (int i = 0; i < expression.length(); i++) {
            c = expression.charAt(i);

            if (c == '(' || c == ')' || c == ',' || operators.has(c)) {
                if (buffer != null) {
                    tokens.add(buffer.toString());
                    buffer = null;
                }
                tokens.add(Character.toString(c));
            } else if (c == 'x' && buffer == null) {
                tokens.add(Character.toString(c));
            } else {
                if (buffer == null) {
                    buffer = new StringBuilder(Character.toString(c));
                } else {
                    buffer.append(c);
                }
            }
        }

        if (buffer != null) {
            tokens.add(buffer.toString());
        }
    }

    /**
     * Prend l'opérateur sur la pile d'opérateurs et ajoute la branche correspondante à l'AST.
     */
    private void pushOperator() {
        Operator op = all.bySymbol(opStack.pop());

        if (op.arity() == Arity.UNARY) {
            stack.push(new Expression(stack.pop(), op));
        } else {
            stack.push(new Expression(stack.pop(), stack.pop(), op, true));
        }
    }

    /**
     * Parse l'expression et retourne un arbre.<br>
     * Utilise l'algorithme de la gare de triage de Dijkstra.
     *
     * @return Un arbre représentant l'expression
     * @throws MismatchParenthesisException En cas de parenthèses non correspondantes
     * @throws MalformedExpressionException En cas d'expression mathématique incorrecte
     * @see <a href="https://en.wikipedia.org/wiki/Shunting-yard_algorithm">Shunting yard algorythm</a>
     * @see <a href="https://fr.wikipedia.org/wiki/Algorithme_Shunting-yard">Algorithme de la gare de triage</a>
     * @see <a href="https://en.wikipedia.org/wiki/Reverse_Polish_notation">Reverse Polish Notation</a>
     */
    public Expression parse() throws MismatchParenthesisException, MalformedExpressionException {
        stack = new Stack<>();
        opStack = new Stack<>();

        Operator o1;

        for (String token : tokens) {

            // nombre
            if (isDouble(token)) {
                stack.push(new Number(token));
            }
            // inconnue
            else if (token.equals("x")) {
                stack.push(new Unknown());
            }
            // fonction
            else if (functions.has(token)) {
                opStack.push(token);
            }
            // opérateur
            else if ((o1 = operators.bySymbol(token)) != null) {
                while (
                    !opStack.empty() && !opStack.peek().equals("(") &&
                        (
                            operators.bySymbol(opStack.peek()).precedence() > o1.precedence() ||
                                (
                                    operators.bySymbol(opStack.peek()).precedence() == o1.precedence()
                                        && o1.isLeftAssociative()
                                )
                        )
                ) {
                    pushOperator();
                }
                opStack.push(token);
            }
            // parenthèse gauche
            else if (token.equals("(")) {
                opStack.push(token);
            }
            // parenthèse droite
            else if (token.equals(")")) {
                while (!opStack.peek().equals("(")) {
                    if (opStack.empty()) throw new MismatchParenthesisException();

                    pushOperator();
                }

                if (!opStack.pop().equals("(")) throw new MismatchParenthesisException();

                if (!opStack.empty() && functions.has(opStack.peek())) {
                    pushOperator();
                }
            } else {
                if (!token.equals(",")) throw new MalformedExpressionException();
            }
        }

        while (!opStack.empty()) {
            if (opStack.peek().equals("(")) throw new MismatchParenthesisException();
            pushOperator();
        }

        if (stack.peek().getClass() == Number.class || stack.peek().getClass() == Unknown.class) {
            return new Expression(stack.pop(), null, null, false);
        }

        return (Expression) stack.pop();
    }
}
