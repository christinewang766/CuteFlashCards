package ui;

import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

import static ui.HelperMethods.makeJOptionButtons;
import static ui.MainGUI.*;

// effects: shows the flashcards loading in

public class LoadDeck {

    protected JPanel loadDeckPanel;
    protected JPanel loadingScreen;
    protected JPanel optionsPanel;
    protected JPanel optionsContainer;

    private Checkbox minimalist;
    private Checkbox cutesy;
    private Checkbox edgy;
    private JCheckBox shuffle;
    private JCheckBox starredOnly;
    private JButton confirmStartChoices;
    private JButton loaded;
    private JPanel in;

    private Color brightTurnips = new Color(255, 168, 183);
    private Color veryLightPink = new Color(255, 242, 242);
    private Font smallCalibri = new Font("Calibri", Font.BOLD, 20);

    public LoadDeck() {
        displayLoadDeck();
//        loadingScreen();
    }

    // effects: sets up the main panel (loadDeckPanel)
    protected void displayLoadDeck() {
        loadDeckPanel = new JPanel();
        loadDeckPanel.setBackground(LIGHT_PINK);
        loadDeckPanel.setLayout(new BoxLayout(loadDeckPanel, BoxLayout.Y_AXIS));
        optionsPanel();
        createButtons();
        createLayout();
    }

    // effects: jazzes up the options panel
    // includes: layout, buttons, background, size
    protected void optionsPanel() {

        optionsPanel = new JPanel(new MigLayout("align 50% 50%"));
        optionsPanel.setBackground(LIGHT_PINK);
        optionsPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        optionsPanel.setBorder(new LineBorder(brightTurnips, 30));

        optionsPanel.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(30, 30, 30, 30, BRIGHT_PINK),
                BorderFactory.createMatteBorder(30, 30, 30, 30, brightTurnips)));

        in = new JPanel(new MigLayout("align 50% 50%"));
        in.setBackground(LIGHT_PINK);
        in.setPreferredSize(new Dimension(WIDTH*7/8-5, 775));
        in.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(30, 30, 30, 30, veryLightPink),
                BorderFactory.createMatteBorder(30, 30, 30, 30, brightTurnips)));
        optionsPanel.add(in, "center");

        loadDeckPanel.add(optionsPanel);
    }


    // effects: create the popup loading page
    public void loadingScreen() {
        loadingScreen = new JPanel();
        loadingScreen.setBackground(LIGHT_PINK);
        loadingScreen.setLayout(new BoxLayout(loadingScreen, BoxLayout.Y_AXIS));

        Icon icon = new ImageIcon("src/images/load.gif");
        JLabel loadingGif = new JLabel(icon);
        loadingGif.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadingScreen.add(Box.createVerticalStrut(160));
        loadingScreen.add(loadingGif);

        loaded = new JButton("Loaded");
        loaded.add(Box.createVerticalStrut(100));
        loaded.setAlignmentX(Component.CENTER_ALIGNMENT);
        makeJOptionButtons(loaded);
        loadingScreen.add(loaded);
        loadDeckPanel.add(loadingScreen, Component.CENTER_ALIGNMENT);
    }

    private void createButtons() {

        CheckboxGroup group = new CheckboxGroup();
        minimalist = new Checkbox("Minimalist", group, true);
        minimalist.setFont(smallCalibri);
        cutesy = new Checkbox("Cutesy", group, false);
        cutesy.setFont(smallCalibri);
        edgy = new Checkbox("Edgy", group, false);
        edgy.setFont(smallCalibri);

        shuffle = new JCheckBox("Shuffle", false);
        customizeJCheckBox(shuffle);
        starredOnly = new JCheckBox("Starred Only", false);
        customizeJCheckBox(starredOnly);

        confirmStartChoices = new JButton("Confirm");
        makeJOptionButtons(confirmStartChoices);
    }

    private void customizeJCheckBox(JCheckBox cb) {
        ImageIcon imageIcon = new ImageIcon("src/images/blackheart.png");
        Image scaledImg = imageIcon.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon("src/images/heart.png");
        Image scaled = image.getImage().getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        cb.setIcon(new ImageIcon(scaledImg));
        cb.setSelectedIcon(new ImageIcon(scaled));
        cb.setFont(smallCalibri);
        cb.setOpaque(false);
    }

    private void createLayout() {
        optionsContainer = new JPanel(new MigLayout("align 50% 50%"));
        optionsContainer.setBackground(veryLightPink);
        optionsContainer.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(30, 30, 30, 30, BRIGHT_PINK),
                        new EmptyBorder(30, 30, 30, 30)));
        JLabel theme = new JLabel("Theme");
        theme.setFont(CONSOLAS);
        theme.setForeground(BRIGHT_PINK);

        JLabel settings = new JLabel("Settings");
        settings.setFont(CONSOLAS);
        settings.setForeground(BRIGHT_PINK);

        JPanel themeButtonsPanel = new JPanel(new MigLayout());
        themeButtonsPanel.setBackground(LIGHT_PINK);
        JPanel settingsPanel = new JPanel(new MigLayout());
        settingsPanel.setBackground(LIGHT_PINK);

        optionsContainer.add(theme, "wrap");
        themeButtonsPanel.add(minimalist, "gapright 25, gapleft 20, center");
        themeButtonsPanel.add(cutesy, "gapright 25, center");
        themeButtonsPanel.add(edgy, "gapright 25, center");
        optionsContainer.add(themeButtonsPanel, "wrap");

        optionsContainer.add(settings, "wrap, gapy 10");
        settingsPanel.add(shuffle,"gapright 25, gapleft 20, center");
        settingsPanel.add(starredOnly,"gapright 90, center");
        optionsContainer.add(settingsPanel, "wrap");

        optionsContainer.add(confirmStartChoices,"center, gapy 20");
        in.add(optionsContainer, "center");
    }

    // getters
    public JButton getConfirmStartChoices() {
        return this.confirmStartChoices;
    }

    public JButton getLoaded() {
        return this.loaded;
    }
}