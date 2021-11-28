package Controllers.Parser;

import Controllers.Operators.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Représente une liste d'opérateurs avec des opérations usuelles.
 *
 * @author Valentin DOREAU
 * @see <a href="https://en.wikipedia.org/wiki/Glossary_of_mathematical_symbols#Arithmetic_operators">List of
 * mathematical arithemtic symbols</a>
 */
public class OperatorList {
    /**
     * Liste des opérateurs.
     */
    private final ArrayList<Operator> operators;

    /**
     * Créé une liste des opérateurs par défaut.
     */
    public OperatorList() {
        this(new ArrayList<>(Arrays.asList(
            new Exponentiation(),

            new Multiplication(),
            new Division(),
            new Modulo(),

            new Addition(),
            new Substraction()
        )));
    }

    /**
     * Créé une liste d'opérateurs issue de la fusion des listes d'opérateurs données.
     *
     * @param lists Liste d'opérateurs à fusionner
     */
    public OperatorList(final OperatorList... lists) {
        operators = new ArrayList<>();
        for (OperatorList lst : lists) {
            operators.addAll(lst.operators);
        }
    }

    /**
     * Créé une liste d'opérateur.
     *
     * @param operators Liste d'opérateur
     */
    public OperatorList(final ArrayList<Operator> operators) {
        this.operators = operators;
    }

    /**
     * Enregistre un nouvel opérateur pour l'application.
     *
     * @param o Opérateur à ajouter
     */
    public void register(final Operator o) {
        operators.add(o);
    }

    /**
     * Recherche un opérateur selon son symbole.
     *
     * @param s Symbole à rechercher
     * @return L'opérateur correspondant au symbole OU null s'il n'existe pas
     */
    public Operator bySymbol(String s) {
        for (final Operator o : operators) {
            if (o.symbol().equals(s)) {
                return o;
            }
        }

        return null;
    }

    /**
     * Détermine si le symbole s existe dans la liste.
     *
     * @param s Symbole à chercher
     * @return true si le symbole existe, faux sinon
     */
    public boolean has(String s) {
        return bySymbol(s) != null;
    }

    /**
     * Détermine si le symbole c existe dans la liste.
     *
     * @param c Symbole à chercher
     * @return true si le symbole existe, faux sinon
     */
    public boolean has(char c) {
        return has(Character.toString(c));
    }
}
