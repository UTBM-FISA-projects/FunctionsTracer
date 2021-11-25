package Views;

import Controllers.Operands.Expression;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Double.NaN;

/**
 * Représente un graphique sur lequel on peut tracer des courbes.
 *
 * @author Valentin DOREAU
 */
public class Graph extends JPanel {
    /**
     * Différence nécessaire en Y entre 2 points pour avoir un nouvel échantillonnage plus précis.
     *
     * @see #fillGap(double, double, double, double, Expression, Graphics2D)
     */
    private static final double GAP_THRESHOLD = 1.5;
    /**
     * Couleur des courbes dessinées.
     */
    private final ArrayList<Color> colors;
    /**
     * Courbes dessinées
     */
    private final ArrayList<Expression> expressions;
    /**
     * Abscisse minimum du graphique.
     */
    private double xMin;
    /**
     * Abscisse maximum du graphique.
     */
    private double xMax;
    /**
     * Ordonnée minimum du graphique.
     */
    private double yMin;
    /**
     * Ordonnée maximum du graphique.
     */
    private double yMax;

    /**
     * Construit un graphique de -10 à 10 en abscisse et en ordonné.
     */
    public Graph() {
        this(-10, 10, -10, 10);
    }

    /**
     * Construit un graphique affichant les intervalles spécifiés.
     *
     * @param xMin Abscisse minimum
     * @param xMax Abscisse maximum
     * @param yMin Ordonnée minimum
     * @param yMax Ordonnée maximum
     */
    public Graph(final double xMin, final double xMax, final double yMin, final double yMax) {
        super();
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        expressions = new ArrayList<>();
        colors = new ArrayList<>();
    }

    /**
     * Ajoute une expression et sa couleur au graphique.
     *
     * @param index index de l'expression à modifier, -1 si c'est une nouvelle expression
     * @param e     Expression
     * @param color Couleur de la courbe
     * @return L'index de l'expression
     */
    public int addExpression(int index, final Expression e, final Color color) {
        final int pos;

        if (index < 0) {
            expressions.add(e);
            colors.add(color);
            pos = expressions.size() - 1;
        } else {
            expressions.remove(index);
            expressions.add(index, e);
            colors.remove(index);
            colors.add(index, color);
            pos = index;
        }

        repaint();
        return pos;
    }

    /**
     * Retire une courbe du graphique.
     *
     * @param index Index de la courbe à retirer
     * @see #addExpression(int, Expression, Color)
     */
    public void removeExpression(int index) {
        if (index >= 0) {
            expressions.remove(index);
            colors.remove(index);
            repaint();
        }
    }

    /**
     * Récupère l'abscisse minimum du graphique.
     *
     * @return X minimum
     */
    public double getXMin() {
        return xMin;
    }

    /**
     * Défini l'abscisse minimum du graphique.
     *
     * @param xMin X minimum
     */
    public void setXMin(final double xMin) {
        this.xMin = xMin;
    }

    /**
     * Récupère l'abscisse maximum du graphique.
     *
     * @return X maximum
     */
    public double getXMax() {
        return xMax;
    }

    /**
     * Défini l'abscisse maximum du graphique.
     *
     * @param xMax X maximum
     */
    public void setXMax(final double xMax) {
        this.xMax = xMax;
    }

    /**
     * Récupère l'ordonnée minimum du graphique.
     *
     * @return Y minimum
     */
    public double getYMin() {
        return yMin;
    }

    /**
     * Défini l'ordonnée minimum du graphique.
     *
     * @param yMin Y minimum
     */
    public void setYMin(final double yMin) {
        this.yMin = yMin;
    }

    /**
     * Récupère l'ordonnée maximum du graphique.
     *
     * @return Y maximum
     */
    public double getYMax() {
        return yMax;
    }

    /**
     * Défini l'ordonnée maximum du graphique.
     *
     * @param yMax Y maximum
     */
    public void setYMax(final double yMax) {
        this.yMax = yMax;
    }

    /**
     * Remplie l'espace entre 2 points en calculant plus de points.
     *
     * @param x1 Abscisse du premier point
     * @param y1 Ordonnée du premier point
     * @param x2 Abscisse du second point
     * @param y2 Ordonnée du second point
     * @param fc Expression actuellement dessinée
     * @param g  Graphics2D avec lequel afficher les points
     * @throws StackOverflowError Cette fonction est récursive, il se peut qu'elle atteigne la limite de récursivité.
     * @see #paintComponent(Graphics)
     */
    private void fillGap(double x1, double y1, double x2, double y2, Expression fc, Graphics2D g) {
        final double vunit = getHeight() / Math.abs(yMin - yMax);
        final double step = (Math.abs(xMin) + Math.abs(xMax)) / getWidth();

        double y3, x3 = (x1 + x2) / 2;

        try {
            y3 = translateY(fc.calculate(xMin + step * x3).toDouble() * vunit);
        } catch (ArithmeticException e) {
            return;
        }
        g.drawLine((int) x3, (int) y3, (int) x3, (int) y3);

        // Si les points sont trop éloignés en Y et qu'ils sont toujours compris dans la fenêtre
        if (Math.abs(y3 - y1) > GAP_THRESHOLD && -getHeight() / 2. < y3 && y3 < getHeight() / 2.) {
            fillGap(x1, y1, x3, y3, fc, g);
        }

        // Si les points sont trop éloignés en Y et qu'ils sont toujours compris dans la fenêtre
        if (Math.abs(y2 - y3) > GAP_THRESHOLD && -getHeight() / 2. < y3 && y3 < getHeight() / 2.) {
            fillGap(x3, y3, x2, y2, fc, g);
        }
    }

    /**
     * Dessine les courbes des fonctions.
     *
     * @param G Graphics de dessin
     */
    @Override
    public void paintComponent(final Graphics G) {
        super.paintComponent(G);
        Graphics2D g = (Graphics2D) G;
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        final int H = this.getHeight();
        final int W = this.getWidth();

        g.translate(0, H / 2);
        drawAxes(g);

        final double vunit = H / Math.abs(yMin - yMax);

        final double step = (Math.abs(xMin) + Math.abs(xMax)) / W;

        g.setStroke(new BasicStroke(1.5f));

        // Pour chaque fonction ...
        for (int j = 0; j < expressions.size(); j++) {
            final Expression fc = expressions.get(j);

            double point, y, lastY = NaN;

            g.setColor(colors.get(j));

            // ... on parcourt la largeur du panel pixel par pixel
            for (int i = 1; i < W; i++) {
                point = xMin + step * i;

                try {
                    y = translateY(fc.calculate(point).toDouble() * vunit);
                } catch (ArithmeticException e) {
                    lastY = NaN;
                    continue;
                }

                // Si les points sont trop éloignés en Y et que le dernier point existe
                if (!Double.isNaN(lastY) && Math.abs(lastY - y) > GAP_THRESHOLD) {
                    try {
                        fillGap(i - 1, lastY, i, y, fc, g);
                    } catch (StackOverflowError ignored) {
                    }
                }

                lastY = y;

                g.drawLine(i, (int) y, i, (int) y);
            }
        }
    }

    /**
     * Dessine les axes du graphique avec une graduation toute les une unité.
     *
     * @param g Graphics2D pour dessiner
     */
    private void drawAxes(Graphics2D g) {
        final int H = this.getHeight();
        final int W = this.getWidth();

        final double vunit = H / Math.abs(yMin - yMax);
        final double hunit = W / Math.abs(xMin - xMax);

        // abscisses
        double count = xMin;
        // graduation tout les 1 unité
        g.drawLine(0, 0, W, 0);
        for (double i = hunit; i < W; i += hunit) {
            g.drawLine((int) i, -10, (int) i, 10);
            g.drawString(String.format("%.0f", ++count), (int) i, -12);
        }

        // ordonnées
        g.drawLine(W / 2, -H / 2, W / 2, H);
        count = yMax;
        // graduation tout les 1 unité
        for (double i = -H / 2. + vunit; i < H; i += vunit) {
            g.drawLine(W / 2 - 10, (int) i, W / 2 + 10, (int) i);
            g.drawString(String.format("%.0f", --count), W / 2 + 12, (int) i);
        }
    }

    /**
     * Traduit les Y mathématiques en Y pouvant être placé sur le panel.<br>
     * En mathématique les positifs sont en haut, en dessin informatique les positifs sont en bas.
     *
     * @param y L'ordonnée à traduire
     * @return L'ordonnée traduite
     */
    private double translateY(double y) {
        return -y;
    }
}
