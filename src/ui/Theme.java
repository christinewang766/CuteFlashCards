package ui;

import model.Deck;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static ui.MainGUI.*;

public abstract class Theme {

    protected JPanel mainPanel;
    protected JTextArea flashcardPanel;
    protected JPanel statisticsBar;
    protected String question;
    protected String answer;
    protected Deck deck;

    public Theme(Deck deck) {
        this.deck = deck;
        question = "question";
        answer = "answer";
        setUpMainPanel();
        setUpFlashCardPanel();
        setUpButtons();
        statistics();
    }

    public void setUpMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(LIGHT_PINK);
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        mainPanel.setLayout(new MigLayout());
    }

//    protected void setUpBackgroundImage(String source) {
//        Background background = new Background(source);
//        mainPanel.add(background);
//    }

    abstract void setUpFlashCardPanel();

    abstract void setUpButtons();

    abstract void statistics();

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
