//package ui;
//import model.Card;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.FocusEvent;
//import java.io.File;
//import java.io.IOException;
//
//import static model.Card.NUM_ATTEMPTS;
//import static ui.HelperMethods.*;
//
//public class AddCardTemplate {
//    protected JPanel addCardPanel;
//
//    private JButton confirm;
//    private JButton cancel;
//    private JButton clear;
//    private Icon icon;
//
//    private String question;
//    private String answer;
//
//    private RoundJTextArea questionArea;
//    private RoundJTextArea answerArea;
//
//    AddCardTemplate() {
//        question = "";
//        answer = "";
//        decoratePanel();
//        makeCButtons();
////        optionPane();
//    }
//
//    private void decoratePanel() {
//        addCardPanel = new JPanel(new GridBagLayout());
//        addCardPanel.setVisible(true);
//
//        questionArea = new RoundJTextArea("Type your question here...\n(140 characters max)");
//        textAreaHelper(questionArea);
//        questionArea.setColumns(20);
//        questionArea.setRows(7);
//        questionArea.setFont(new Font("Calibri", Font.BOLD, 25));
//        questionArea.addFocusListener(this);
//
//        answerArea = new RoundJTextArea("Type your answer here...\n(140 characters max)");
//        answerArea.setColumns(20);
//        answerArea.setRows(7);
//        textAreaHelper(answerArea);
//        answerArea.setFont(new Font("Calibri", Font.BOLD, 25));
//        answerArea.addFocusListener(this);
//
//        addCardPanel.add(questionArea);
//        addCardPanel.add(answerArea);
//
//        String[] options = new String[2];
//        options[0] = "Confirm";
//        options[1] = "Cancel";
//
//        try {
//            // PLACEHOLDER ICON
//            Image img = ImageIO.read(new File("src/images/heart.png"));
//            icon = new ImageIcon(img);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // effects: sets up the option panel
//    protected void optionPane() {
//        Object[] textLabels = {"Question:", questionArea, "Answer:", answerArea};
//        JButton[] buttons = { confirm, cancel };
//
//        JOptionPane.showOptionDialog(createDeckPanel, textLabels, "Add Card",
//                JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, icon,
//                buttons, buttons[0]);
//    }
//
//    // effects: create the confirm and cancel button
//    private void makeCButtons() {
//        confirm = new JButton("Confirm");
//        makeJOptionButtons(confirm);
////        confirm.addActionListener(this);
//        cancel = new JButton("Cancel");
//        makeJOptionButtons(cancel);
////        cancel.addActionListener(this);
//        clear = new JButton("Clear");
//        makeJOptionButtons(clear);
////        clear.addActionListener(this);
//    }
//
//    // effects: checks if the answer/question text is < 140 char
//    private void charCountCheck(FocusEvent e) {
//        JTextArea textArea = (JTextArea) e.getSource();
//        if (textArea.getText().length() > 140) {
//            int charOver = textArea.getText().length() - 140;
//            String message = "You are " + charOver + " characters\nover the limit!";
//            createNoButtonJOption(addCardPanel, message, "Character Max Reached", "src/images/knife.png");
//        } else {
//            if (e.getSource() == questionArea) {
//                question = textArea.getText();
//            } else if (e.getSource() == answerArea) {
//                answer = textArea.getText();
//            }
//        }
//    }
//
//    // effects: checks if both the question and answer text areas
//    // satisfy criteria and prints out appropriate message if not
//    protected void finalCharCheck() {
//        int charOver = questionArea.getText().length() - 140;
//        if (questionArea.getText().length() > 140) {
//            String message = "Your question is " + charOver + " characters\nover the limit!" +
//                    "\nPlease fix your question before creating your card.";
//            createNoButtonJOption(addCardPanel, message,
//                    "Not to be mean...but...","src/images/knife.png");
//        } else if (answerArea.getText().length() > 140) {
//            String message = "Your answer is " + charOver + " characters\nover the limit!" +
//                    "\nPlease fix your answer before creating your card.";
//            createNoButtonJOption(addCardPanel, message,
//                    "Not to be mean...but...","src/images/knife.png");
//        } else {
//            question = questionArea.getText();
//            answer = answerArea.getText();
//            newCard = new Card(question, answer, false, false, NUM_ATTEMPTS, true);
//            deck.addFlashCard(newCard);
//        }
//    }
//
//    // getters
//    public JButton getConfirmButton() {
//        return confirm;
//    }
//
//    public JButton getCancelButton() {
//        return cancel;
//    }
//
//    public JButton getClearButton() {
//        return clear;
//    }
//
//    public String getQuestion() {
//        return question;
//    }
//
//    public String getAnswer() {
//        return answer;
//    }
//
//        @Override
//    // saves the title entered
//    public void focusLost(FocusEvent e) {
//        if (e.getSource() == questionArea) {
//            charCountCheck(e);
//        } else if (e.getSource() == answerArea) {
//            charCountCheck(e);
//        }
//    }
//}