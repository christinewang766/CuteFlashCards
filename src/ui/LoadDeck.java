package ui;

import javax.swing.*;

import static ui.TitlePage.LIGHT_PINK;

public class LoadDeck {

    protected JPanel loadDeckPanel;
    // effects: shows the flashcards loading in
    protected void displayLoadDeck() {
        loadDeckPanel = new JPanel();
        loadDeckPanel.setBackground(LIGHT_PINK);
        loadDeckPanel.setLayout(new BoxLayout(loadDeckPanel, BoxLayout.Y_AXIS));

//        titleButtons(titleOptionsPanel);
    }

}
