package ui;

import model.Deck;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

import static ui.HelperMethods.makeJOptionButtons;
import static ui.MainGUI.*;

// effects: shows the flashcards loading in

public class LoadDeck {

    private Deck deck;
    protected JPanel loadDeckPanel;
    protected JPanel loadingScreen;
    protected JPanel optionsPanel;
    protected JPanel optionsContainer;

    private ButtonGroup group;
    private JRadioButton minimalist;
    private JRadioButton cutesy;
    private JRadioButton edgy;
    private JCheckBox shuffle;
    private JCheckBox starredOnly;
    private JButton confirmStartChoices;
    private JButton loaded;
    private JPanel in;

    private Color veryLightPink = new Color(255, 242, 242);
    private Font smallCalibri = new Font("Calibri", Font.BOLD, 20);

    public LoadDeck(Deck deck) {
        this.deck = deck;
        displayLoadDeck();
        loadingScreen();
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
        optionsPanel.setBorder(new LineBorder(BRIGHT_TURNIPS, 30));

        optionsPanel.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(30, 30, 30, 30, BRIGHT_PINK),
                BorderFactory.createMatteBorder(30, 30, 30, 30, BRIGHT_TURNIPS)));

        in = new JPanel(new MigLayout("align 50% 50%"));
        in.setBackground(LIGHT_PINK);
        in.setPreferredSize(new Dimension(WIDTH*7/8-5, 775));
        in.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(30, 30, 30, 30, veryLightPink),
                BorderFactory.createMatteBorder(30, 30, 30, 30, BRIGHT_TURNIPS)));
        optionsPanel.add(in, "center");
        optionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadDeckPanel.add(optionsPanel, Component.CENTER_ALIGNMENT);
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
        loaded.addActionListener(e -> optionsPanel.setVisible(true));
        makeJOptionButtons(loaded);
        loadingScreen.add(loaded);
        loadDeckPanel.add(loadingScreen, Component.CENTER_ALIGNMENT);
    }

    private void createButtons() {

        group = new ButtonGroup();
        minimalist = new JRadioButton("Minimalist", true);
        minimalist.setFont(smallCalibri);
        minimalist.setOpaque(false);
        cutesy = new JRadioButton("Cutesy", false);
        cutesy.setFont(smallCalibri);
        cutesy.setOpaque(false);
        edgy = new JRadioButton("Edgy", false);
        edgy.setFont(smallCalibri);
        edgy.setOpaque(false);
        group.add(minimalist);
        group.add(cutesy);
        group.add(edgy);

        shuffle = new JCheckBox("Shuffle", false);
        customizeJCheckBox(shuffle);
        starredOnly = new JCheckBox("Starred Only", false);
        customizeJCheckBox(starredOnly);

        confirmStartChoices = new JButton("Confirm");
        makeJOptionButtons(confirmStartChoices);
        confirmStartChoices.addActionListener(e -> {
            System.out.println("start");
            if (minimalist.isSelected()) {
                System.out.println("minimalist");
            } if (cutesy.isSelected()) {
                System.out.println("cutesey");
            } if (edgy.isSelected()) {
                System.out.println("edgy");
            } if (shuffle.isSelected()) {
                if (starredOnly.isSelected()) {
                    deck.setFlashCards(deck.starredOnly());
                    deck.shuffle(deck.getFlashCards());
                } else {
                    deck.shuffle(deck.getFlashCards());
                }
            } if (starredOnly.isSelected() && !shuffle.isSelected()) {
                deck.setFlashCards(deck.starredOnly());
            }
        });
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
        JLabel theme = new JLabel("ThemeSetting");
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

    public JRadioButton getMinimalist() {
        return this.minimalist;
    }

    public JRadioButton getCutesy() {
        return this.cutesy;
    }

    public JRadioButton getEdgy() {
        return this.edgy;
    }

    public JCheckBox getShuffle() {
        return this.shuffle;
    }

    public JCheckBox getStarredOnly() {
        return this.starredOnly;
    }

}