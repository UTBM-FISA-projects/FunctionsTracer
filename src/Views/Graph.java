package Views;

import Controllers.Operands.Expression;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

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
     * Nombre minimal de pixels dans une unité en abscisse.
     *
     * @see #drawAxes(Graphics2D)
     */
    private static final short X_MIN_UNIT = 40;
    /**
     * Nombre maximal de pixels dans une unité en abscisse.
     */
    private static final short X_MAX_UNIT = 100;
    /**
     * Nombre minimal de pixels dans une unité en ordonnée.
     *
     * @see #drawAxes(Graphics2D)
     */
    private static final short Y_MIN_UNIT = 30;
    /**
     * Nombre maximal de pixels dans une unité en ordonnée.
     */
    private static final short Y_MAX_UNIT = 90;

    /**
     * Couleur des courbes dessinées.
     */
    private final Map<Integer, Color> colors;
    /**
     * Courbes dessinées
     */
    private final Map<Integer, Expression> expressions;

    /**
     * Paramètres du graphique
     */
    private final GraphParameters p;

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
        p = new GraphParameters(xMin, xMax, yMin, yMax);
        expressions = new HashMap<>();
        colors = new HashMap<>();
    }

    /**
     * Ajoute une expression et sa couleur au graphique.
     *
     * @param code  Clé sous laquelle stocker l'expression et la couleur
     * @param e     Expression
     * @param color Couleur de la courbe
     */
    public void addExpression(int code, final Expression e, final Color color) {
        expressions.put(code, e);
        colors.put(code, color);
        repaint();
    }

    /**
     * Retire une courbe du graphique, s'il ne reste qu'une courbe ne fait rien et renvoie false.
     *
     * @param code clé à supprimer
     * @return false s'il reste moins d'une courbe, true sinon
     * @see #addExpression(int, Expression, Color)
     */
    public boolean removeExpression(int code) {
        if (expressions.size() <= 1) return false;

        expressions.remove(code);
        colors.remove(code);
        repaint();

        return true;
    }

    /**
     * Défini l'abscisse minimum du graphique et le repeint.
     *
     * @param xMin X minimum
     */
    public void setXMin(final double xMin) {
        p.xMin = xMin;
        repaint();
    }

    /**
     * Défini l'abscisse maximum du graphique et le repeint.
     *
     * @param xMax X maximum
     */
    public void setXMax(final double xMax) {
        p.xMax = xMax;
        repaint();
    }

    /**
     * Défini l'ordonnée minimum du graphique et le repeint.
     *
     * @param yMin Y minimum
     */
    public void setYMin(final double yMin) {
        p.yMin = yMin;
        repaint();
    }

    /**
     * Défini l'ordonnée maximum du graphique et le repeint.
     *
     * @param yMax Y maximum
     */
    public void setYMax(final double yMax) {
        p.yMax = yMax;
        repaint();
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
        double y3, x3 = (x1 + x2) / 2;

        try {
            y3 = translateY(fc.calculate(p.xMin + p.unitPerXPixel * x3).toDouble());
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
     * Dessine les courbes des fonctions.<br>
     * Met à jour les paramètres de dessin en premier.
     *
     * @param G Graphics de dessin
     * @see GraphParameters#update()
     */
    @Override
    public void paintComponent(final Graphics G) {
        super.paintComponent(G);
        Graphics2D g = (Graphics2D) G;

        if (p.yReverse() || p.xReverse()) {
            g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(), 20));
            g.setColor(Color.RED);
            g.drawString("Définition du graphique incorrecte !", 10, 30);
            return;
        }

        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        p.update();

        g.translate(0, getHeight() / 2);
        drawAxes(g);

        g.setStroke(new BasicStroke(1.5f));

        Expression fc;

        // Pour chaque fonction ...
        for (final Map.Entry<Integer, Expression> entry : expressions.entrySet()) {
            fc = entry.getValue();

            double point, y, lastY = NaN;

            g.setColor(colors.get(entry.getKey()));

            // ... on parcourt la largeur du panel pixel par pixel
            for (int i = 1; i < getWidth(); i++) {
                point = p.xMin + p.unitPerXPixel * i;

                try {
                    y = translateY(fc.calculate(point).toDouble());
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
     * Dessine les axes du graphique avec la graduation.<br>
     * La graduation s'ajuste en fonction du zoom.
     *
     * @param g Graphics2D pour dessiner
     */
    private void drawAxes(Graphics2D g) {
        double yFactor = 1, xFactor = 1;

        while (p.pixelPerYUnit * yFactor < Y_MIN_UNIT) yFactor *= 2;
        while (p.pixelPerYUnit * yFactor > Y_MAX_UNIT) yFactor /= 2;

        while (p.pixelPerXUnit * xFactor < X_MIN_UNIT) xFactor *= 2;
        while (p.pixelPerXUnit * xFactor > X_MAX_UNIT) xFactor /= 2;

        double index = p.xMin;

        // abscisses
        g.drawLine(0, 0, getWidth(), 0);
        // graduation
        for (double i = p.pixelPerXUnit * xFactor; i < getWidth(); i += p.pixelPerXUnit * xFactor) {
            g.drawLine((int) i, -10, (int) i, 10);
            g.drawString(String.format("%s", new DecimalFormat().format(index += xFactor)), (int) i - 5, -15);
        }

        index = p.yMax;

        // ordonnées
        g.drawLine(getWidth() / 2, -getHeight() / 2, getWidth() / 2, getHeight());
        // graduation
        for (double i = -getHeight() / 2. + p.pixelPerYUnit * yFactor; i < getHeight(); i += p.pixelPerYUnit * yFactor) {
            g.drawLine(getWidth() / 2 - 10, (int) i, getWidth() / 2 + 10, (int) i);
            g.drawString(String.format("%s", new DecimalFormat().format(index -= yFactor)), getWidth() / 2 + 15, (int) i + 2);
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
        return (-y + (p.yMax + p.yMin) / 2) * p.pixelPerYUnit;
    }

    /**
     * Wrapper pour les paramètres de dessin du graphique.<br>
     * Évite de recalculer les paramètres entre les différentes fonctions de dessins.
     *
     * @author Valentin DOREAU
     * @see Graph
     */
    private class GraphParameters {
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
         * Nombre de pixels dans une unité en X.
         */
        private double pixelPerXUnit;
        /**
         * Nombre de pixels dans une unité en Y.
         */
        private double pixelPerYUnit;

        /**
         * Nombre d'unités dans un pixel en X.
         */
        private double unitPerXPixel;

        /**
         * Créé un objet contenant tous les paramètres de dessin du {@link Graph}.
         *
         * @param xMin abscisse minimum du graphique
         * @param xMax abscisse maximum du graphique
         * @param yMin ordonnée minimum du graphique
         * @param yMax ordonnée maximum du graphique
         */
        public GraphParameters(final double xMin, final double xMax, final double yMin, final double yMax) {
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
            update();
        }

        /**
         * Recalcule les paramètres de dessins.<br>
         * Utilise en cas de redimensionnement ou de changement de limite du graphique.
         */
        public void update() {
            pixelPerXUnit = getWidth() / Math.abs(xMax - xMin);
            pixelPerYUnit = getHeight() / Math.abs(yMax - yMin);
            unitPerXPixel = Math.abs(xMax - xMin) / getWidth();
        }

        /**
         * Détermine si l'axe des abscisses est renversé.
         *
         * @return true si xMax est strictement plus petit que xMin, false sinon
         */
        public boolean xReverse() {
            return xMax < xMin;
        }

        /**
         * Détermine si l'axe des ordonnées est renversé.
         *
         * @return true si yMax est strictement plus petit que yMin, false sinon
         */
        public boolean yReverse() {
            return yMax < yMin;
        }
    }
}
