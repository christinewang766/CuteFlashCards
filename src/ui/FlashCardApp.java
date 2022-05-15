package ui;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

import static ui.MainGUI.BRIGHT_PINK;

public class FlashCardApp {

    public static void main(String[] args) throws FileNotFoundException {
        // credits: https://stackoverflow.com/questions/2482971/how-to-change-the-color-of-titlebar-in-jframe
        UIDefaults uiDefaults = UIManager.getDefaults();
        uiDefaults.put("activeCaption", new javax.swing.plaf.ColorUIResource(BRIGHT_PINK));
        uiDefaults.put("activeCaptionText", new javax.swing.plaf.ColorUIResource(Color.white));
        JFrame.setDefaultLookAndFeelDecorated(true);

        new MainGUI();
    }
}
