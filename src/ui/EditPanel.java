package ui;

import model.Card;
import model.Deck;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static javax.swing.BorderFactory.createEmptyBorder;
import static ui.CreateCards.CHAR_LIMIT;
import static ui.HelperMethods.*;
import static ui.MainGUI.*;

public class EditPanel {

    private JPanel cardPanel;
    private JButton starButton;
    private JButton deleteButton;
    private JTextArea questionCardArea;
    private JTextArea answerCardArea;
    private Card card;
    private Deck deck;
    private int index;
    private CreateCards cc;

    public EditPanel(Deck deck, Card card, CreateCards cc) {
        this.cc = cc;
        this.deck = deck;
        this.card = card;
        this.index = 0;
    }

    // effects: sets up the layout of the editing panel
    protected JPanel showCard() {
        cardPanel = new JPanel(new MigLayout("align 50% 50%"));
        cardPanel.setBackground(BRIGHT_TURNIPS);
        cardPanel.setPreferredSize(new Dimension(WIDTH * 3 / 7, HEIGHT * 2 / 5 - 30));

        questionCardArea = new JTextArea(card.getQuestion());
        questionCardArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        questionCardArea.setLineWrap(true);
        answerCardArea = new JTextArea(card.getAnswer());
        answerCardArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        answerCardArea.setLineWrap(true);

        JScrollPane question = new JScrollPane(questionCardArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane answer = new JScrollPane(answerCardArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollHelper(question, questionCardArea);
        scrollHelper(answer, answerCardArea);

        createButtons();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BRIGHT_TURNIPS);
        buttonPanel.add(starButton);
        buttonPanel.add(deleteButton);
        cardPanel.add(buttonPanel, "gapleft 250, wrap");
        cardPanel.add(labelHelper("Question: "), "wrap");
        cardPanel.add(question, "wrap");
        cardPanel.add(labelHelper("Answer: "), "wrap");
        cardPanel.add(answer);
        return cardPanel;
    }

    private void createButtons() {
        starButton = new JButton("Star");
        cardButtonHelper(starButton, "src/images/star.png");
        starButton.addActionListener(e -> {
            deck.getFlashCards().get(index).setStarred(true);
        });

        questionCardArea.addFocusListener(new FListen(questionCardArea));
        answerCardArea.addFocusListener(new FListen(answerCardArea));

        deleteButton = new JButton("Delete");
        cardButtonHelper(deleteButton, "src/images/delete.png");
        deleteButton.addActionListener(e -> {
            deck.getFlashCards().remove(this.index);
            cc.displayCreatedCards();
        });
    }

    // effects: helper to set up the star, edit, and delete button
    private void cardButtonHelper(JButton button, String source) {
        button.setFont(new Font("Consolas", Font.BOLD, 15));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(0, BRIGHT_TURNIPS, BRIGHT_PINK, Color.pink, Color.white),
                createEmptyBorder(5, 5, 5, 5)));
        button.setForeground(Color.white);
        button.setBackground(BRIGHT_PINK);
        button.setIcon(createSmallIcon(source, 15, 15));
    }

    // effects: helper to set up the scroll area to display question/answer
    private void scrollHelper(JScrollPane scrollPane, JTextArea area) {
        scrollPane.setPreferredSize(new Dimension(WIDTH * 2 / 5 - 100, 100));
        scrollPane.setWheelScrollingEnabled(true);
        area.setFont(new Font("Calibri", Font.PLAIN, 20));
        area.setEditable(false);
        area.setBackground(new Color(255, 245, 245));
    }

    // effects: helper to set up the labels Question and Answer
    private JLabel labelHelper(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Consolas", Font.BOLD, 25));
        label.setForeground(Color.white);
        return label;
    }

    // effects: sets the index (ID of the card)
    protected void changeIndex(int index) {
        this.index = index;
    }

    private class FListen implements FocusListener {
        JTextArea textArea;

        public FListen(JTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void focusGained(FocusEvent e) {
            Border b;
            b = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BRIGHT_PINK, 5),
                    new EmptyBorder(10, 10, 10, 10));
            textArea.setBorder(b);
            textArea.setEditable(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textArea == questionCardArea) {
                if (questionCardArea.getText().length() > CHAR_LIMIT) {
                    int charOver = questionCardArea.getText().length() - CHAR_LIMIT;
                    String message = "The question is " + charOver + " characters\nover the limit!";
                    createNoButtonJOption(cardPanel, message,
                            "Character Max Reached", "src/images/knife.png",
                            110, 110);
                    questionCardArea.requestFocus();
                } else {
                    deck.getFlashCards().get(index).setQuestion(questionCardArea.getText());
                }
            } if (textArea == answerCardArea) {
                if (answerCardArea.getText().length() > CHAR_LIMIT) {
                    int charOver = answerCardArea.getText().length() - CHAR_LIMIT;
                    String message = "The answer is " + charOver + " characters\nover the limit!";
                    createNoButtonJOption(cardPanel, message,
                            "Character Max Reached", "src/images/knife.png",
                            110, 110);
                    answerCardArea.requestFocus();
                } else {
                    deck.getFlashCards().get(index).setAnswer(answerCardArea.getText());
                }
            }
            textArea.setBorder(new EmptyBorder(10, 10, 10, 10));
            textArea.setEditable(false);
        }
    }
}
