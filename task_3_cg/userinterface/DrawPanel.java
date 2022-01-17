package task_3_cg.userinterface;

import task_3_cg.Main;
import task_3_cg.entity.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DrawPanel extends JPanel {
    private final Timer t;
    private final double ratio;
    private final ArrayList<Point> tmpList = new ArrayList<>();
    public ArrayList<Point> list = new ArrayList<>();
    private double time;
    private int velocity;
    private int xCordChoose = -1;
    private int yCordChoose = -1;
    private boolean secondEditStep = false;
    private int editableIndex = -1;
    private boolean removingAnimation = false;
    private int removingIndex = -1;

    public DrawPanel() {

        ratio = 0.3;
        velocity = 4;
        time = 0;
        t = new Timer(velocity, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int xM = MouseInfo.getPointerInfo().getLocation().x;
                int yM = MouseInfo.getPointerInfo().getLocation().y;

                Main.settingsFrame.mX.setText("" + xM);
                Main.settingsFrame.mY.setText("" + yM);

                Main.settingsFrame.toFront();
                time += ratio;

                if (time > 1) {
                    repaint();
                    time = 0;
                }

                for (int i = 0; i < list.size(); i++) {
                    Point point1 = list.get(i);
                    Point point2 = tmpList.get(i);

                    if (point1.getX() > point2.getX()) {
                        point1.setX(point1.getX() - 1);
                    }
                    if (point1.getX() < point2.getX()) {
                        point1.setX(point1.getX() + 1);
                    }
                    if (point1.getY() > point2.getY()) {
                        point1.setY(point1.getY() - 1);
                    }
                    if (point1.getY() < point2.getY()) {
                        point1.setY(point1.getY() + 1);
                    }
                }
            }
        });
        
        t.start();
        setList();
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
        t.setDelay(velocity);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gr = (Graphics2D) g;
        if (removingAnimation) {
            if (tmpList.get(removingIndex).getX() == list.get(removingIndex).getX() &&
                    tmpList.get(removingIndex).getY() == list.get(removingIndex).getY()) {
                list.remove(removingIndex);
                tmpList.remove(removingIndex);
                removingAnimation = false;
            }
        }
        drawScene(gr);
    }

    private void drawScene(Graphics2D g) {
        RenderRules renderRules = new RenderRules(g, this);
        renderRules.drawBackground();
        if (list.size() > 0) {
            renderRules.drawPointALPHA(list);
        }
        if (list.size() > 0) {
            renderRules.drawPointBETA(tmpList);
        }
        if (list.size() > 1) {
            renderRules.drawLine(list);
        }
        if (secondEditStep) {
            renderRules.drawCHOSEN(xCordChoose, yCordChoose);
        }
    }

    private int searchProcess(int xCord, int yCord) {
        int current = -1;
        double prevVector = 99999;
        for (int i = 0; i < tmpList.size(); i++) {
            int currX = tmpList.get(i).getX();
            int currY = tmpList.get(i).getY();
            int chXC = currX - xCord;
            int chYC = currY - yCord;
            double vector = Math.sqrt(chXC * chXC + chYC * chYC);
            if (vector < prevVector) {
                current = i;
                prevVector = vector;
            }
        }
        return current;
    }

    private void setList() {
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (secondEditStep) {
                    Main.settingsFrame.helper.setText("");
                    int newX = e.getX();
                    int newY = e.getY();
                    tmpList.set(editableIndex, new Point(newX, newY));
                    secondEditStep = false;
                    Main.settingsFrame.editPointButton.setEnabled(true);
                    Main.settingsFrame.removePointButton.setEnabled(true);
                    Main.settingsFrame.addPointButton.setEnabled(true);
                    Main.settingsFrame.xChoosen.setVisible(false);
                    Main.settingsFrame.yChoosen.setVisible(false);
                }
                if (Main.editProcess) {
                    int x = e.getX();
                    int y = e.getY();
                    Main.settingsFrame.xChoosen.setVisible(true);
                    Main.settingsFrame.yChoosen.setVisible(true);
                    Main.settingsFrame.xChoosen.setText("" + x);
                    Main.settingsFrame.yChoosen.setText("" + y);
                    int currentIndex = searchProcess(x, y);
                    repaint();
                    secondEditStep = true;
                    Main.editProcess = false;
                    editableIndex = currentIndex;
                    xCordChoose = list.get(editableIndex).getX();
                    yCordChoose = list.get(editableIndex).getY();
                    Main.settingsFrame.helper.setText("click to new position on main frame");
                }

                if (Main.removeProcess) {
                    int x = e.getX();
                    int y = e.getY();
                    int currentIndex = searchProcess(x, y);
                    if (currentIndex != -1) {
                        removingAnimation = true;
                        removingIndex = currentIndex;
                        tmpList.set(currentIndex, countMediana(removingIndex));
                        repaint();
                    }
                    Main.removeProcess = false;
                    Main.settingsFrame.helper.setText("");
                    Main.settingsFrame.editPointButton.setEnabled(true);
                    Main.settingsFrame.removePointButton.setEnabled(true);
                    Main.settingsFrame.addPointButton.setEnabled(true);
                }

                if (Main.addProcess) {
                    if (list.size() > 0) {
                     //   list.add(new Point(Main.dp.getWidth() / 2,
                        //        Main.dp.getHeight() / 2));
                        Point p = list.get(list.size() - 1);

                        list.add(new Point(p.getX(), p.getY()));
                    } else {
                        list.add(new Point(e.getX(), e.getY()));
                    }
                    tmpList.add(new Point(e.getX(), e.getY()));
                    repaint();
                    Main.addProcess = false;
                    Main.settingsFrame.editPointButton.setEnabled(true);
                    Main.settingsFrame.removePointButton.setEnabled(true);
                    Main.settingsFrame.addPointButton.setEnabled(true);
                    Main.settingsFrame.helper.setText("");
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
    }

    private Point countMediana(int index) {
        ArrayList<Point> toWork = new ArrayList<>(list);
        if (index == -1 || index == 0) {
            int xSum = 0;
            for (Point point : toWork) {
                xSum += point.getX();
            }
            int ySum = 0;
            for (Point point : toWork) {        
                ySum += point.getY();
            }

            return new Point(xSum / toWork.size(), ySum / toWork.size());
        } else {
            toWork.remove(index);
            int xSum = 0;
            int ySum = 0;
            for (Point point : toWork) {
                ySum += point.getY();
                xSum += point.getX();
            }
            return new Point(xSum / toWork.size(), ySum / toWork.size());
        }
    }
}