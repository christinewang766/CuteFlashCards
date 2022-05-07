package ui;

import javax.swing.*;

import static ui.TitlePage.lightPink;

public class CreateCards {

    protected JPanel createDeckPanel;

    // effects: shows the flashcards being made
    protected void displayCreateDeck() {
        createDeckPanel = new JPanel();
        createDeckPanel.setBackground(lightPink);
        createDeckPanel.setLayout(new BoxLayout(createDeckPanel, BoxLayout.Y_AXIS));
        createDeckPanel.add(new JTextField("Start Typing Your New Flashcard!", 20));

//        titleButtons(titleOptionsPanel);
    }
}
