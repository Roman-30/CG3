package task_3_cg;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Framey {

    private JFrame frame;

    public Framey() {
        initialize();
    }

    private static int fact(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    private static double bernstein(float t, int n, int i) {
        return (fact(n) / (float) (fact(i) * fact(n - i))) * Math.pow(1 - t, n - i) * Math.pow(t, i);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Framey window = new Framey();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setBounds(23, 35, 650, 600);
        frame.getContentPane().add(panel);


        JSpinner spinner = new JSpinner();
        spinner.setBounds(691, 113, 30, 20);
        frame.getContentPane().add(spinner);

        JLabel warning = new JLabel("Value cannot be larger than 8!");
        warning.setFont(new Font("Tahoma", Font.BOLD, 9));
        warning.setBounds(283, 144, 141, 53);
        frame.getContentPane().add(warning);
        warning.setVisible(false);

        JButton btnNewButton = new JButton("New button");

        btnNewButton.addActionListener(e -> {
            int pointCount;

            pointCount = (int) spinner.getValue();
            warning.setVisible(pointCount > 8);


            Point[] points = getPoints(pointCount, panel);

            drawScene(points, panel);
        });

        panel.add(btnNewButton);
    }

    private Point[] getPoints(int pointCount, Component panel) {
        Random val = new Random();
        Point[] points = new Point[pointCount];
        for (int i = 0; i < pointCount; i++) {
            points[i] = new Point(val.nextInt(panel.getWidth()), val.nextInt(panel.getHeight()));
        }
        return points;
    }

    private void drawScene(Point[] points, Component panel) {
        int pWidth = panel.getWidth();
        int pHeight = panel.getHeight();
        Graphics g = panel.getGraphics();
        int pointCount = points.length;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, pWidth, pHeight);

        if (pointCount > 1) {
            float t = 0;
            while (t <= 1) {
                g.setColor(Color.DARK_GRAY);
                besierCurve(t, points, panel);
                t += 0.0001;
            }
        }
    }

    private void besierCurve(float t, Point[] points, Component panel) {
        Graphics g = panel.getGraphics();
        int pointCount = points.length;
        double[] bPoly = new double[pointCount];
        for (int i = 0; i < pointCount; i++) {
            bPoly[i] = bernstein(t, pointCount - 1, i + 1);
        }

        double sumX = 0;
        double sumY = 0;

        for (int i = 0; i < pointCount; i++) {
            sumX += bPoly[i] * points[i].x;
            sumY += bPoly[i] * points[i].y;
        }

        int x, y;
        x = (int) Math.round(sumX);
        y = (int) Math.round(sumY);
        g.drawLine(x, y, x, y);
    }
}
