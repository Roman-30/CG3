package task_3_cg.userinterface;

import task_3_cg.Main;

import javax.swing.*;
import java.awt.*;

public class Settings extends JFrame {
    public JButton addPointButton;
    public JButton editPointButton;
    public JButton removePointButton;
    public JLabel helper;
    public JLabel xChoosen;
    public JLabel yChoosen;
    public JLabel mX;
    public JLabel mY;
    private JPanel contentPane;
    private JSlider slider1;
    private JLabel velocityShow;
    private JLabel pointsCount;
    private JLabel status;

    public Settings() {
        setContentPane(contentPane);
        setBounds(0, 0, 480, 170);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        xChoosen.setVisible(false);
        yChoosen.setVisible(false);


        slider1.addChangeListener(e -> {
            int changeable = slider1.getValue();
            Main.dp.editVelocity(changeable);
            toFront();
            velocityShow.setText("" + slider1.getValue());
        });

        addPointButton.addActionListener(e -> {
            addPointButton.setEnabled(false);
            editPointButton.setEnabled(false);
            removePointButton.setEnabled(false);
            status.setText("ADD");
            status.setForeground(Color.GREEN);
            Main.addProcess = true;
            Main.settingsFrame.helper.setText("choose place to createPoint");
            pointsCount.setText(" " + (Main.dp.count() + 1));
        });

        editPointButton.addActionListener(e -> {
            status.setText("EDIT");
            status.setForeground(Color.YELLOW);
            addPointButton.setEnabled(false);
            editPointButton.setEnabled(false);
            removePointButton.setEnabled(false);
            Main.settingsFrame.helper.setText("choose any point to edit");
            Main.editProcess = true;
        });

        removePointButton.addActionListener(e -> {
            addPointButton.setEnabled(false);
            editPointButton.setEnabled(false);
            removePointButton.setEnabled(false);
            status.setText("REMOVE");
            status.setForeground(Color.red);
            Main.removeProcess = true;
            pointsCount.setText(" " + (Main.dp.count() - 1));
        });
    }
}
