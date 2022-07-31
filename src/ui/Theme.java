package ui;

import model.Card;
import model.Deck;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import static ui.HelperMethods.*;
import static ui.MainGUI.*;

public abstract class Theme {

    protected JPanel mainPanel;
    protected JPanel statisticsBar;

    protected JLabel cardsLeft;
    protected JLabel percentAccuracy;

    protected JTextField answerField;
    protected JTextArea flashcardPanel;

    protected String question;
    protected String answer;

    protected JButton skip;
    protected JButton edit;
    protected JCheckBox star;

    private DecimalFormat oneDecimal;
    private double percentageCorrect;

    protected Deck deck;
    protected CreateCards cc;

    public Theme(Deck deck, CreateCards cc) {
        this.deck = deck;
        this.cc = cc;
        init();
        setUpMainPanel();
        setUpFlashcardPanel();
        customFlashcardPanel();
        setUpButtons();
        customButtons();
        setUpAnswerField();
        statistics();
        statisticsCustom();
    }

    protected void setUpFlashcardPanel() {
        if (deck.hasMoreCards()) {
            deck.getNextCard();
            if (!deck.getCurrentCard().getStarred()) {
                star.setSelected(false);
            }
            flashcardPanel.setText(deck.getCurrentCard().getQuestion());
        } else {
            flashcardPanel.setText(deck.summary());
            deck.resetCards();
            cc.saveDeck(deck.getTitle());
        }
    }

    private void init() {
        question = "question";
        answer = "answer";
        flashcardPanel = new JTextArea();
        skip = new JButton("Skip >>");
        star = new JCheckBox(" Star", false);
        edit = new JButton("Edit");
        statisticsBar = new JPanel(new MigLayout());
        oneDecimal = new DecimalFormat("#.#");
        percentageCorrect = 100 * (double) deck.countCorrect() / (double) deck.getCompletedFlashCards().size();
    }

    private void setUpButtons() {
        skip.addActionListener(e -> {
            if (deck.hasMoreCards()) {
                deck.getFlashCards().add(deck.getCurrentCard());
                deck.getNextCard();
                flashcardPanel.setText(deck.getCurrentCard().getQuestion());
            } else {
                createNoButtonJOption(mainPanel,"This is the last card!", "C'mon now...",
                        "src/images/sorry.png", 120, 120);
            }
        });

        star.addActionListener(e -> {
            for (Card card: updatedCards()) {
                if (deck.getCurrentCard().getQuestion() == card.getQuestion()) {
                    if (star.isSelected()) {
                        deck.getCurrentCard().setStarred(true);
                        card.setStarred(true);
                    } else {
                        deck.getCurrentCard().setStarred(false);
                        card.setStarred(false);
                    }
                    cc.saveDeck(deck.getTitle());
                }
            }
        });

        edit.addActionListener(e -> createEditPopUp());
    }

    private void createEditPopUp() {
        JTextField question = new JTextField(deck.getCurrentCard().getQuestion());
        JTextField answer = new JTextField(deck.getCurrentCard().getAnswer());

        JButton saveEdit = new JButton("Save");
        saveEdit.addActionListener(e -> {

            for (Card card : deck.getCards()) {
                if (deck.getCurrentCard().getQuestion().equals(card.getQuestion())) {
                    card.setQuestion(question.getText());
                    card.setAnswer(answer.getText());
                }
            }

            for (Card card: updatedCards()) {
                if (deck.getCurrentCard().getQuestion() == card.getQuestion()) {
                    card.setQuestion(question.getText());
                    card.setAnswer(answer.getText());
                    cc.saveDeck(deck.getTitle());
                }
            }

            deck.getCurrentCard().setQuestion(question.getText());
            deck.getCurrentCard().setAnswer(answer.getText());
            flashcardPanel.setText(question.getText());

            closeCurrentWindow();
        });

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(e -> closeCurrentWindow());

        Object[] textLabels = {"Question:", question, "Answer:", answer};
        JButton[] buttons = {saveEdit, cancel};

        JOptionPane.showOptionDialog(mainPanel, textLabels, "Edit Card",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                createSmallIcon("src/images/saveEdits.png", 120, 120),
                buttons, buttons[0]);
    }

    private ArrayList<Card> updatedCards() {
        ArrayList<Card> updatedCards = new ArrayList<>();
        for (Card card: deck.getCompletedFlashCards()) {
            updatedCards.add(card);
        }
        for (Card card: deck.getUnfinishedFlashcards()) {
            updatedCards.add(card);
        }
        return updatedCards;
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
                    if (deck.getCards().size() > deck.getCompletedFlashCards().size()) {
                        setAnswer(answerField.getText());
                        answerField.setText("");
                        deck.submitAnswer(getAnswer());
                        setUpFlashcardPanel();
                        cardsLeft.setText("Cards Left: " + deck.getCountOfUnfinishedFlashcards());
                        percentAccuracy.setText("Accuracy: " + oneDecimal.format(percentageCorrect) + "%");
                    }
                }
            }
        });
    }

    // effects: calculates how many cards are left to do and the
    // accuracy of the user; creates JLabel for both
    private void statistics() {
        cardsLeft = new JLabel("Cards Left: " + deck.getFlashCards().size());

        if ((double) deck.getCompletedFlashCards().size() == 0) {
            percentAccuracy = new JLabel("Accuracy: 0%");
        } else {
            percentAccuracy = new JLabel("Accuracy: " + oneDecimal.format(percentageCorrect) + "%");
        }
    }

    // effects: when the user closes the application using the x button,
    // they are prompted with a choice to save their position or not
    // credits: https://stackoverflow.com/questions/13419947/java-message-when-closing-jframe-window
    private void closeMidDeck() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit the program?", "Exit Program Message Box",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    abstract void customFlashcardPanel();

    abstract void customButtons();

    abstract void statisticsCustom();

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return this.answer;
    }
}
