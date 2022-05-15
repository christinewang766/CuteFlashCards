package ui;

import model.Card;
import model.Deck;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static javax.swing.BorderFactory.createEmptyBorder;
import static ui.HelperMethods.createSmallIcon;
import static ui.MainGUI.*;

public class EditPanel {

    private Deck deck;
    private Card card;
    private JButton starButton;
    private JButton editButton;
    private JButton deleteButton;
    private JTextArea questionCardArea;
    private JTextArea answerCardArea;
    private String question;
    private String answer;
    private EditPanelFocusListener fListen;

    public EditPanel(Deck deck, Card card) {
        fListen = new EditPanelFocusListener();
        this.deck = deck;
        this.card = card;
        printCardToScroll();
    }
    protected JPanel printCardToScroll() {
        JPanel cardPanel = new JPanel(new MigLayout("align 50% 50%"));
        cardPanel.setBackground(BRIGHT_TURNIPS);
        cardPanel.setPreferredSize(new Dimension(WIDTH*2/5, HEIGHT*2/5-30));

        questionCardArea = new JTextArea(card.getQuestion());
        answerCardArea = new JTextArea(card.getAnswer());

        JScrollPane question = new JScrollPane(questionCardArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane answer = new JScrollPane(answerCardArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollHelper(question, questionCardArea);
        scrollHelper(answer, answerCardArea);

        starButton = new JButton("Star");
        cardButtonHelper(starButton, "src/images/star.png");
        editButton = new JButton("Edit");
        cardButtonHelper(editButton, "src/images/pencil.png");
        editButton.addActionListener(e -> {
            questionCardArea.addFocusListener(fListen);
            answerCardArea.addFocusListener(fListen);
            questionCardArea.setEditable(true);
            answerCardArea.setEditable(true);
        });
        deleteButton = new JButton("Delete");
        cardButtonHelper(deleteButton, "src/images/delete.png");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BRIGHT_TURNIPS);
        buttonPanel.add(starButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        cardPanel.add(buttonPanel, "gapleft 335, wrap");
        cardPanel.add(labelHelper("Question: "), "wrap");
        cardPanel.add(question,"wrap");
        cardPanel.add(labelHelper("Answer: "), "wrap");
        cardPanel.add(answer);
        return cardPanel;
    }

    // effects: helper to set up the star, edit, and delete button
    private void cardButtonHelper(JButton button, String source) {
        button.setFont(new Font("Consolas", Font.BOLD, 15));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createBevelBorder(0,BRIGHT_TURNIPS, BRIGHT_PINK, Color.pink, Color.white),
                createEmptyBorder(5, 5, 5, 5)));
        button.setForeground(Color.white);
        button.setBackground(BRIGHT_PINK);
        button.setIcon(createSmallIcon(source, 15,15));
    }

    // effects: helper to set up the scroll area to display question/answer
    private void scrollHelper(JScrollPane scrollPane, JTextArea area) {
        scrollPane.setPreferredSize(new Dimension(WIDTH*2/5-50,100));
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

    // getters and setters
    public void setCard(Card newCard) {
        this.card = newCard;
    }

    public JButton getDeleteButton() {
        return this.deleteButton;
    }

    public JButton getStarButton() {
        return this.deleteButton;
    }

   class EditPanelFocusListener implements FocusListener {

       @Override
       public void focusGained(FocusEvent e) {
           if (e.getSource() == questionCardArea) {
               question = questionCardArea.getText();
           }
           if (e.getSource() == answerCardArea) {
               answer = answerCardArea.getText();
           }
       }

       @Override
       public void focusLost(FocusEvent e) {
           if (e.getSource() == questionCardArea) {
               for (Card card : deck.getCards()) {
                   if (card.getQuestion().equals(question)) {
                       card.setQuestion(questionCardArea.getText());
                   }
               }
               deck.setFlashCards(deck.getCards());
               questionCardArea.removeFocusListener(fListen);
               answerCardArea.removeFocusListener(fListen);
               questionCardArea.setEditable(false);
               answerCardArea.setEditable(false);
           }
           if (e.getSource() == answerCardArea) {
               for (Card card : deck.getCards()) {
                   if (card.getAnswer().equals(answer)) {
                       card.setAnswer(answerCardArea.getText());
                   }
               }
               deck.setFlashCards(deck.getCards());
               questionCardArea.removeFocusListener(fListen);
               answerCardArea.removeFocusListener(fListen);
               questionCardArea.setEditable(false);
               answerCardArea.setEditable(false);
           }
       }
   }
}
