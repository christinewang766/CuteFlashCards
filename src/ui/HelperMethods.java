package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static javax.swing.BorderFactory.createEmptyBorder;
import static ui.CreateCards.CALIBRI_BOLD;
import static ui.MainGUI.*;
import static ui.MainGUI.HEIGHT;

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
            Image scaledImg = img.getScaledInstance(368 / 2, 111 / 2, java.awt.Image.SCALE_SMOOTH);
            ImageIcon buttonImage = new ImageIcon(scaledImg);
            button.setIcon(buttonImage);
            button.setFont(smallCalibri);
            buttonHelper(button);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // effects: creates an icon from given source
    public static ImageIcon createSmallIcon(String source, int width, int height) {
        // credit: Emekat http://pixelartmaker.com/art/ad1ae9084485dac
        Image img = null;
        try {
            img = ImageIO.read(new File(source));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaledImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImg);
        return icon;
    }

    // effects: makes a JOptionPane without a button to warn user
    public static void createNoButtonJOption(JPanel panel, String message, String title, String source,
                                             int width, int height) {
        frameChanges();
        JOptionPane.showOptionDialog(panel, message, title,
                JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
                createSmallIcon(source, width, height), new Object[]{}, null);
    }

    // effects: changes the background of JOption and other UI panels
    public static void frameChanges() {
        UIManager frameChanges = new UIManager();
        frameChanges.put("OptionPane.background",LIGHT_PINK);
        frameChanges.put("Panel.background",LIGHT_PINK);
        UIManager.put("OptionPane.messageFont", new Font("Consolas", Font.BOLD, 25));
        UIManager.put("OptionPane.buttonFont", CALIBRI_BOLD);
    }

    // effects: closes the current window or JOptionPane
    public static void closeCurrentWindow() {
        // credits: https://stackoverflow.com/questions/18105598/closing-a-joptionpane-programmatically
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JDialog) {
                JDialog dialog = (JDialog) window;
                if (dialog.getContentPane().getComponentCount() == 1
                        && dialog.getContentPane().getComponent(0) instanceof JOptionPane) {
                    dialog.dispose();
                }
            }
        }
    }

    public static JViewport backgroundViewPort(String source) {
        // assigns a static background image
        ImageIcon imageIcon = new ImageIcon(source);
        Image image = imageIcon.getImage();
        Image scaledImg = image.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_SMOOTH);

        // credit: https://coderanch.com/t/700884/java/static-background-scrollpane
        JViewport viewport = new JViewport() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(scaledImg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        return viewport;
    }

    // effects: brings you back to the main title page,
    // congratulates/prompts you to save your spot/progress
    public static JButton createMenuButton() {
        JButton menuButton = new JButton("Menu");
        makeJOptionButtons(menuButton);
        return menuButton;
    }
}
