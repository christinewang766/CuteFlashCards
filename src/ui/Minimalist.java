package ui;

import model.Deck;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BorderFactory.createEmptyBorder;
import static ui.MainGUI.HEIGHT;
import static ui.MainGUI.WIDTH;

public class Minimalist extends Theme {

    public final static Color sageGreen = new Color(180,192,173,255);
    private JPanel buttonPanel;


    public Minimalist(Deck deck) {
        super(deck);
        questionFrame();
        typeAnswerHereBar();
    }

    // effects: creates the white rectangle frame for question display
    private void questionFrame() {
        JPanel whiteRec = new JPanel(new MigLayout("align 50% 50%"));
        whiteRec.setPreferredSize(new Dimension(WIDTH, HEIGHT-(80+70)));
        whiteRec.setBackground(Color.white);
        mainPanel.add(whiteRec, "north, wrap");
        whiteRec.add(flashcardPanel, "wrap");
        whiteRec.add(statisticsBar);
    }

    @Override
    protected void customFlashcardPanel() {
        flashcardPanel.setBackground(Color.white);
        flashcardPanel.setForeground(new Color(85, 96, 80,255));
        flashcardPanel.setPreferredSize(new Dimension(WIDTH*4/7, HEIGHT*4/7));
        flashcardPanel.setFont(new Font("Calibri", Font.PLAIN, 40));
        flashcardPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(sageGreen, 6),
                BorderFactory.createEmptyBorder(30,30,30,30)));
        flashcardPanel.setEditable(false);
        flashcardPanel.setLineWrap(true);
    }

    @Override
    protected void setUpButtons() {
        buttonPanelBar();
        buttonHelper(skip);
        buttonPanel.add(skip, "gapright 50");
        buttonHelper(star);
        buttonPanel.add(star, "gapright 50");
        buttonHelper(edit);
        buttonPanel.add(edit);
        mainPanel.add(buttonPanel);
    }

    private void buttonHelper(JButton button) {
        button.setFont(new Font("Calibri", Font.BOLD, 30));
        button.setForeground(Color.white);
        button.setBorderPainted(false);
        button.setBackground(sageGreen);
    }

    @Override
    protected void statisticsCustom() {
        statisticsBar.setPreferredSize(new Dimension(WIDTH*4/7, 45));
        statisticsBar.setBackground(Color.white);

        cardsLeft.setFont(new Font("Calibri", Font.BOLD, 25));
        cardsLeft.setForeground(sageGreen);
        statisticsBar.add(cardsLeft, "gapright 50");

        percentAccuracy.setFont(new Font("Calibri", Font.BOLD, 25));
        percentAccuracy.setForeground(sageGreen);
        statisticsBar.add(percentAccuracy);

    }

    private void buttonPanelBar() {
        buttonPanel = new JPanel(new MigLayout());
        buttonPanel.setPreferredSize(new Dimension(WIDTH, 70));
        buttonPanel.setBackground(sageGreen);
    }

    // effects: creates the bottom bar to type answer
    private void typeAnswerHereBar() {
        answerField.setFont(new Font("Calibri", Font.ITALIC, 40));
        answerField.setForeground(new Color(157,157,157,255));
        answerField.setBorder(createEmptyBorder(10, 10, 10, 10));
        answerField.setPreferredSize(new Dimension(WIDTH, 80));
        answerField.setMinimumSize(new Dimension(WIDTH, 80));
        answerField.setMaximumSize(new Dimension(WIDTH, 80));
        answerField.setBackground(new Color(252, 250, 239, 255));
        mainPanel.add(answerField, "south");
    }

}
