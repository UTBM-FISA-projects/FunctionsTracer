package Views;

import Controllers.Operands.Expression;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graph extends JPanel {
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

    @Override
    public void paint(final Graphics G) {
        Graphics2D g = (Graphics2D) G;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int H = this.getHeight();
        final int W = this.getWidth();

        g.translate(0, H / 2);
        drawAxes(g);

        final double vunit = H / Math.abs(yMin - yMax);
        final double hunit = W / Math.abs(xMin - xMax);

        final double step = (Math.abs(xMin) + Math.abs(xMax)) / W;

        g.setStroke(new BasicStroke(1.5f));

        for (Expression fc : expressions) {

            g.setColor(Color.RED);

            double point;

            for (int i = 0; i < W; i++) {
                point = xMin + step * i;
                int y;

                try {
                    y = translateY((int) (fc.calculate(point).toDouble() * vunit));
                } catch (ArithmeticException e) {
                    continue;
                }

                g.drawLine(i, y, i, y);
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

    private int translateY(int y) {
        return -y;
    }
}
