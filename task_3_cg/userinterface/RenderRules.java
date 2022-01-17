package task_3_cg.userinterface;

import task_3_cg.entity.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderRules {
    private final Graphics g;

    private final JPanel panel;

    public RenderRules(Graphics insertedG, JPanel frame) {
        this.g = insertedG;
        this.panel = frame;
    }

    private static double bernstein(float t, int n, int i) {

        return (fact(n) / (float) (fact(i) * fact(n - i))) * Math.pow(1 - t, n - i) * Math.pow(t, i);
    }

    private static int fact(int n) {
        if (n <= 1) {
            return 1;
        }
        return fact(n - 1) * n;
    }

    public void drawBackground() {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

        g.setColor(new Color(0xD7D7D7));
        int supGH = panel.getHeight();
        int supGW = panel.getWidth();
        for (int i = 0; i < supGH / 20; i++) {
            g.drawLine(0, i * 20, supGW, i * 20);
        }
        for (int i = 0; i < supGW / 20; i++) {
            g.drawLine(i * 20, 0, i * 20, supGH);
        }
    }

    public void drawPointALPHA(ArrayList<Point> tmpList) {
        int i = 1;
        for (Point point : tmpList) {
            g.setColor(Color.RED);
            g.drawOval(point.getX(), point.getY() - 6, 5, 5);
            String coordsVis = "{" + i + "}" + "[x=" + point.getX() + ";y=" + point.getY() + "]";
            g.drawString(coordsVis, point.getX() + 10, point.getY() + 10);
            i++;
        }
    }

    public void drawPointBETA(ArrayList<Point> list) {
        int i = 1;
        for (Point point : list) {
            g.setColor(Color.BLUE);
            g.drawOval(point.getX(), point.getY() - 6, 5, 5);
            String coordsVis = "{" + i + "}" + "[x=" + point.getX() + ";y=" + point.getY() + "]";
            g.drawString(coordsVis, point.getX() + 10, point.getY() + 10);
            i++;
        }
    }

    public void drawLine(ArrayList<Point> list) {
        float precision = 0.0001f;
        float t = 0;
        while (t <= 1) {
            g.setColor(Color.BLACK);
            calculateCurvePixel(t, (Graphics2D) g, list);
            t += precision;
        }
    }

    private void calculateCurvePixel(float t, Graphics2D g, ArrayList<Point> list) {
        //int k = 0;
        double[] pixelCof = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            pixelCof[i] = bernstein(t, list.size() - 1, i + 1);
        }
        double sumX = 0;
        double sumY = 0;
        for (int i = 0; i < list.size(); i++) {
            sumX += pixelCof[i] * list.get(i).getX();
            sumY += pixelCof[i] * list.get(i).getY();
        }
        g.drawLine((int) Math.round(sumX), (int) Math.round(sumY),
                (int) Math.round(sumX), (int) Math.round(sumY));
    }

    public void drawCHOSEN(int xCordChoose, int yCordChoose) {
        g.setColor(Color.GREEN);
        g.drawOval(xCordChoose, yCordChoose, 5, 5);
    }
}
