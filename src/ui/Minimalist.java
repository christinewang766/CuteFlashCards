package ui;

import model.Deck;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static javax.swing.BorderFactory.createEmptyBorder;
import static ui.MainGUI.HEIGHT;
import static ui.MainGUI.WIDTH;

public class Minimalist extends Theme {

    public final static Color sageGreen = new Color(180,192,173,255);


    public Minimalist(Deck deck) {
        super(deck);
        questionFrame();
        buttonPanelBar();
        typeAnswerHereBar();
    }

    // effects: creates the white rectangle frame for question display
    private void questionFrame() {
        JPanel whiteRec = new JPanel(new MigLayout("align 50% 50%"));
        whiteRec.setPreferredSize(new Dimension(WIDTH, HEIGHT-(80+70)));
        whiteRec.setBackground(Color.white);
        mainPanel.add(whiteRec, "north");
        whiteRec.add(flashcardPanel, "wrap");
        whiteRec.add(statisticsBar);
    }

    @Override
    protected void setUpFlashCardPanel() {
        flashcardPanel = new JTextArea();
        flashcardPanel.setBackground(Color.white);
        flashcardPanel.setForeground(new Color(85, 96, 80,255));
        flashcardPanel.setPreferredSize(new Dimension(WIDTH*4/7, HEIGHT*4/7));
        flashcardPanel.setFont(new Font("Calibri", Font.PLAIN, 40));
        flashcardPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(sageGreen, 6),
                BorderFactory.createEmptyBorder(30,30,30,30)));
        flashcardPanel.setEditable(false);
        flashcardPanel.setLineWrap(true);
        flashcardPanel.setText("Question");
    }

    @Override
    protected void setUpButtons() {

    }

    @Override
    protected void statistics() {
        statisticsBar = new JPanel(new MigLayout("align 50% 50%"));
        statisticsBar.setPreferredSize(new Dimension(WIDTH*4/7, 45));
        statisticsBar.setBackground(Color.pink);

    }

    private void buttonPanelBar() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(WIDTH, 70));
        buttonPanel.setMinimumSize(new Dimension(WIDTH, 70));
        buttonPanel.setMaximumSize(new Dimension(WIDTH, 70));
        buttonPanel.setBackground(sageGreen);
        mainPanel.add(buttonPanel);
    }

    // effects: creates the bottom bar to type answer
    private void typeAnswerHereBar() {
        JTextField answerField = new JTextField();
        String defaultText = "Type your answer here...";
        answerField.setText(defaultText);
        answerField.setFont(new Font("Calibri", Font.ITALIC, 40));
        answerField.setForeground(new Color(157,157,157,255));
        answerField.setBorder(createEmptyBorder(10, 10, 10, 10));
        answerField.setPreferredSize(new Dimension(WIDTH, 80));
        answerField.setMinimumSize(new Dimension(WIDTH, 80));
        answerField.setMaximumSize(new Dimension(WIDTH, 80));
        answerField.setBackground(new Color(252, 250, 239, 255));
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
                }
            }
        });
        mainPanel.add(answerField, "south");

    }

}
