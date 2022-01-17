package task_3_cg;

import task_3_cg.userinterface.MainFrame;
import task_3_cg.userinterface.Settings;

public class Main {

    public static boolean addProcess;
    public static boolean editProcess;
    public static boolean removeProcess;

    public static MainFrame dp;
    public static Settings settingsFrame;

    public static void main(String[] args) {
        addProcess = false;
        editProcess = false;
        removeProcess = false;

        settingsFrame = new Settings();
        dp = new MainFrame();

    }

}
