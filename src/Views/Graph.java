package Views;

import Controllers.Operands.Expression;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static java.lang.Double.NaN;

public class Graph extends JPanel {
    private static final double Y_THRESHOLD = 1.5;

    private final ArrayList<Expression> expressions;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;

    public Graph() {
        this(-10, 10, -10, 10);
    }

    public Graph(final double xMin, final double xMax, final double yMin, final double yMax) {
        super();
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        expressions = new ArrayList<>();
    }

    public void addExpression(Expression e) {
        expressions.add(e);
    }

    public double getXMin() {
        return xMin;
    }

    public void setXMin(final double xMin) {
        this.xMin = xMin;
    }

    public double getXMax() {
        return xMax;
    }

    public void setXMax(final double xMax) {
        this.xMax = xMax;
    }

    public double getYMin() {
        return yMin;
    }

    public void setYMin(final double yMin) {
        this.yMin = yMin;
    }

    public double getYMax() {
        return yMax;
    }

    public void setYMax(final double yMax) {
        this.yMax = yMax;
    }

    private void fillGap(double x1, double y1, double x2, double y2, Expression fc, Graphics2D g) {
        final double vunit = getHeight() / Math.abs(yMin - yMax);
        final double step = (Math.abs(xMin) + Math.abs(xMax)) / getWidth();

        double x3 = (x1 + x2) / 2;
        double y3;
        try {
            y3 = translateY(fc.calculate(xMin + step * x3).toDouble() * vunit);
        } catch (ArithmeticException e) {
            return;
        }
        g.drawLine((int) x3, (int) y3, (int) x3, (int) y3);

        if (Math.abs(y3 - y1) > Y_THRESHOLD && -getHeight() / 2. < y3 && y3 < getHeight() / 2.) {
            fillGap(x1, y1, x3, y3, fc, g);
        }

        if (Math.abs(y2 - y3) > Y_THRESHOLD && -getHeight() / 2. < y3 && y3 < getHeight() / 2.) {
            fillGap(x3, y3, x2, y2, fc, g);
        }
    }

    @Override
    public void paint(final Graphics G) {
        Graphics2D g = (Graphics2D) G;
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        final int H = this.getHeight();
        final int W = this.getWidth();

        g.translate(0, H / 2);
        drawAxes(g);

        final double vunit = H / Math.abs(yMin - yMax);

        final double step = (Math.abs(xMin) + Math.abs(xMax)) / W;

        g.setStroke(new BasicStroke(1.5f));

        for (final Expression fc : expressions) {
            double point, lastY = NaN;

            g.setColor(Color.BLUE);

            for (int i = 1; i < W; i++) {
                point = xMin + step * i;
                double y;

                g.setStroke(new BasicStroke(1.5f));
                try {
                    y = translateY(fc.calculate(point).toDouble() * vunit);
                } catch (ArithmeticException e) {
                    lastY = NaN;
                    continue;
                }

                if (!Double.isNaN(lastY) && Math.abs(lastY - y) > Y_THRESHOLD) {
                    fillGap(i - 1, lastY, i, y, fc, g);
                }

                lastY = y;

                g.drawLine(i, (int) y, i, (int) y);
            }
        }
    }

    private void drawAxes(Graphics2D g) {
        final int H = this.getHeight();
        final int W = this.getWidth();

        final double vunit = H / Math.abs(yMin - yMax);
        final double hunit = W / Math.abs(xMin - xMax);

        // abscisses
        double count = xMin;
        g.drawLine(0, 0, W, 0);
        for (double i = hunit; i < W; i += hunit) {
            g.drawLine((int) i, -10, (int) i, 10);
            g.drawString(String.format("%.0f", ++count), (int) i, -12);
        }

        // ordonnées
        g.drawLine(W / 2, -H / 2, W / 2, H);
        count = yMax;
        for (double i = -H / 2. + vunit; i < H; i += vunit) {
            g.drawLine(W / 2 - 10, (int) i, W / 2 + 10, (int) i);
            g.drawString(String.format("%.0f", --count), W / 2 + 12, (int) i);
        }
    }

    private double translateY(double y) {
        return -y;
    }
}
