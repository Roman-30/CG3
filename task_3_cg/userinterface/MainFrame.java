package task_3_cg.userinterface;

import javax.swing.*;

public class MainFrame extends JFrame {

    private final DrawPanel drawPanel;

    public MainFrame() {
        this.setBounds(0, 0, 1000, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setResizable(false);

        drawPanel = new DrawPanel();
        this.add(drawPanel);
    }

    public void editVelocity(int value) {
        drawPanel.setVelocity(value);
    }

    public int count() {
        return drawPanel.list.size();
    }
}
