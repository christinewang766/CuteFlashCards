package ui;

import model.Deck;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;

import static ui.MainGUI.*;

public abstract class Theme {

    protected JPanel mainPanel;
    protected JTextArea flashcardPanel;
    protected JPanel statisticsBar;
    protected String question;
    protected String answer;
    protected Deck deck;
    protected JTextField answerField;
    protected JButton skip;
    protected JButton edit;
    protected JButton star;
    protected JLabel cardsLeft;
    protected JLabel percentAccuracy;

    public Theme(Deck deck) {
        this.deck = deck;
        init();
        setUpMainPanel();
        setUpFlashcardPanel();
        customFlashcardPanel();
        setUpButtons();
        setUpAnswerField();
        statistics();
        statisticsCustom();
    }

    protected void setUpFlashcardPanel() {
        if (deck.hasMoreCards()) {
            deck.getNextCard();
            flashcardPanel.setText(deck.getCurrentCard().getQuestion());
        } else {
            flashcardPanel.setText(deck.summary());
        }
    }

    private void init() {
        question = "question";
        answer = "answer";
        flashcardPanel = new JTextArea();
        skip = new JButton("Skip >>");
        star = new JButton("Star");
        edit = new JButton("Edit");
        statisticsBar = new JPanel(new MigLayout());
    }

    private void setUpMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(LIGHT_PINK);
        mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        mainPanel.setLayout(new MigLayout());
    }

    private void setUpAnswerField() {
        answerField = new JTextField();
        String defaultText = "Type your answer here...";
        answerField.setText(defaultText);
        answerField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    setAnswer(answerField.getText());
                    answerField.setText("");
                    deck.submitAnswer(getAnswer());
                    setUpFlashcardPanel();
                }
            }
        });
    }

    private void statistics() {

        DecimalFormat oneDecimal = new DecimalFormat("#.#");
        double percentageCorrect = 100 * (double) deck.countCorrect() / (double) deck.getCompletedFlashCards().size();

        cardsLeft = new JLabel("Cards Left: " + deck.getUnfinishedFlashcards().size());


        if ((double) deck.getCompletedFlashCards().size() == 0) {
            percentAccuracy = new JLabel("Accuracy: 0%");
        } else {
            percentAccuracy = new JLabel("Accuracy: " + oneDecimal.format(percentageCorrect) + "%");
        }
    }

//    protected void setUpBackgroundImage(String source) {
//        Background background = new Background(source);
//        mainPanel.add(background);
//    }

    abstract void customFlashcardPanel();

    abstract void setUpButtons();

    abstract void statisticsCustom();

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return this.answer;
    }
}
