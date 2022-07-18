package ui;

import model.Deck;

import javax.swing.*;
import java.awt.*;

import static ui.HelperMethods.makeJOptionButtons;
import static ui.MainGUI.*;

// effects: shows the flashcards loading in

public class LoadDeck {

    private Deck deck;
    protected JPanel loadDeckPanel;
    protected JPanel loadingScreen;
    private JButton loaded;
    private CreateCards cc;

    public LoadDeck(Deck deck, CreateCards cc) {
        this.deck = deck;
        this.cc = cc;
        displayLoadDeck();
        loadingScreen();
    }

    // effects: sets up the main panel (loadDeckPanel)
    protected void displayLoadDeck() {
        loadDeckPanel = new JPanel();
        loadDeckPanel.setBackground(LIGHT_PINK);
        loadDeckPanel.setLayout(new BoxLayout(loadDeckPanel, BoxLayout.Y_AXIS));
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


    public JButton getLoadedButton() {
        return loaded;
    }

}