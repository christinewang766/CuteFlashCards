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
import static ui.MainGUI.BRIGHT_TURNIPS;

public class Settings {

    private Deck deck;
    private CreateCards cc;
    protected JPanel optionsContainer;
    protected JPanel settingsPanel;

    private JRadioButton minimalist;
    private JRadioButton cutesy;
    private JRadioButton edgy;
    private JCheckBox shuffle;
    private JCheckBox starredOnly;
    private JButton confirmStartChoices;
    private JPanel in;

    private final Color veryLightPink = new Color(255, 242, 242);
    private final Font smallCalibri = new Font("Calibri", Font.BOLD, 20);

    public Settings(Deck deck, CreateCards cc) {
        this.deck = deck;
        this.cc = cc;
        optionsPanel();
        createButtons();
        createLayout();
    }

    // effects: jazzes up the options panel
    // includes: layout, buttons, background, size
    protected void optionsPanel() {

        settingsPanel = new JPanel(new MigLayout("align 50% 50%"));
        settingsPanel.setBackground(LIGHT_PINK);
        settingsPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        settingsPanel.setBorder(new LineBorder(BRIGHT_TURNIPS, 30));

        settingsPanel.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(30, 30, 30, 30, BRIGHT_PINK),
                BorderFactory.createMatteBorder(30, 30, 30, 30, BRIGHT_TURNIPS)));

        in = new JPanel(new MigLayout("align 50% 50%"));
        in.setBackground(LIGHT_PINK);
        in.setPreferredSize(new Dimension(WIDTH/2, HEIGHT/2));
        in.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(30, 30, 30, 30, veryLightPink),
                BorderFactory.createMatteBorder(30, 30, 30, 30, BRIGHT_TURNIPS)));
        settingsPanel.add(in, "center, gapy 90, gapbottom 90");

        settingsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void createButtons() {

        ButtonGroup group = new ButtonGroup();
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

        // TODO TEMPORARY
        confirmStartChoices.addActionListener(e -> {
            System.out.println("start");
            deck.setCards(deck.getFlashCards());
            if (shuffle.isSelected()) {
                if (starredOnly.isSelected()) {
                    deck.setFlashCards(deck.starredOnly());
                    deck.shuffle(deck.getFlashCards());
                } else {
                    deck.shuffle(deck.getFlashCards());
                }
            } if (starredOnly.isSelected() && !shuffle.isSelected()) {
                deck.setFlashCards(deck.starredOnly());
            }
            if (minimalist.isSelected()) {
                Minimalist min = new Minimalist(deck, cc);
                System.out.println("minimalist");
            } if (cutesy.isSelected()) {
                Minimalist min = new Minimalist(deck, cc);
                System.out.println("cutesey");
            } if (edgy.isSelected()) {
                Minimalist min = new Minimalist(deck, cc);
                System.out.println("edgy");
            }
        });
    }

    // effects: makes the checkbox a heart shape, sets font
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

    // effects: sets up the layout and spacing of components
    private void createLayout() {
        optionsContainer = new JPanel(new MigLayout("align 50% 50%"));
        optionsContainer.setBackground(veryLightPink);
        optionsContainer.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(30, 30, 30, 30, BRIGHT_PINK),
                new EmptyBorder(30, 30, 30, 30)));
        JLabel theme = new JLabel("temp");
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
}

