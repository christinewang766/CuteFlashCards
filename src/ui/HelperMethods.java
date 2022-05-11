package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static javax.swing.BorderFactory.createEmptyBorder;
import static ui.CreateCards.CALIBRI_BOLD;
import static ui.MainGUI.BRIGHT_PINK;
import static ui.MainGUI.LIGHT_PINK;

public class HelperMethods {

    // modifies: this
    // effects: sets up and aligns buttons allignment
    public static void buttonHelper(JButton button) {
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(BRIGHT_PINK);
    }

    // effects: text area attributes
    public static void textAreaHelper(JTextArea text) {
        text.setForeground(BRIGHT_PINK);
        text.setBackground(new Color(255, 245, 245));
        text.setBorder(createEmptyBorder());
        text.setBorder(BorderFactory.createCompoundBorder(
                text.getBorder(),
                createEmptyBorder(15, 15, 15, 15)));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
    }

    // effects: creates attributes of JOptionPanel buttons
    public static void makeJOptionButtons(JButton button) {
        try {
            Font smallCalibri = new Font("Calibri", Font.BOLD, 25);
            Image img = ImageIO.read(new File("src/images/button.png"));
            Image scaledImg = img.getScaledInstance(368/2, 111/2, java.awt.Image.SCALE_SMOOTH);
            ImageIcon buttonImage = new ImageIcon(scaledImg);
            button.setIcon(buttonImage);
            button.setFont(smallCalibri);
            buttonHelper(button);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // effects: makes a JOptionPane without a button to warn user
    public static void createNoButtonJOption(JPanel panel, String message, String title, String source) {
        try {
            // credit: Emekat http://pixelartmaker.com/art/ad1ae9084485dac
            Image img = ImageIO.read(new File(source));
            Image scaledImg = img.getScaledInstance(130, 140, java.awt.Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImg);

            frameChanges();

            JOptionPane.showOptionDialog(panel, message, title,
                    JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, icon, new Object[]{}, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // effects: changes the background of JOption and other UI panels
    public static void frameChanges() {
        UIManager frameChanges = new UIManager();
        frameChanges.put("OptionPane.background",LIGHT_PINK);
        frameChanges.put("Panel.background",LIGHT_PINK);
        UIManager.put("OptionPane.messageFont", new Font("Consolas", Font.BOLD, 25));
        UIManager.put("OptionPane.buttonFont", CALIBRI_BOLD);
    }
}
