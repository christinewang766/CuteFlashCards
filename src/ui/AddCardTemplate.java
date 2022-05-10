package ui;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;

import static ui.MainGUI.*;

public class AddCardTemplate extends CreateCards implements ActionListener {
    protected JPanel addCardPanel;

    private JButton confirm;
    private JButton cancel;
    private Icon icon;

    private String question;
    private String answer;

    private RoundJTextArea questionArea;
    private RoundJTextArea answerArea;

    AddCardTemplate() {
        decoratePanel();
        makeCButtons();
        optionPane();
    }

    private void decoratePanel() {
        addCardPanel = new JPanel(new GridBagLayout());

        questionArea = new RoundJTextArea("Type your question here...\n(140 characters max)");
        textAreaHelper(questionArea);
        questionArea.setColumns(20);
        questionArea.setRows(7);
        questionArea.setFont(new Font("Calibri", Font.BOLD, 25));
        questionArea.addFocusListener(this);

        answerArea = new RoundJTextArea("Type your answer here...\n(140 characters max)");
        answerArea.setColumns(20);
        answerArea.setRows(7);
        textAreaHelper(answerArea);
        answerArea.setFont(new Font("Calibri", Font.BOLD, 25));
        answerArea.addFocusListener(this);

        addCardPanel.add(questionArea);
        addCardPanel.add(answerArea);
        UIManager.put("OptionPane.messageFont", new Font("Consolas", Font.BOLD, 25));
        UIManager.put("OptionPane.buttonFont", CALIBRI_BOLD);

        String[] options = new String[2];
        options[0] = "Confirm";
        options[1] = "Cancel";

        try {
            // PLACEHOLDER ICON
            Image img = ImageIO.read(new File("src/images/heart.png"));
            icon = new ImageIcon(img);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UIManager frameChanges = new UIManager();
        frameChanges.put("OptionPane.background",LIGHT_PINK);
        frameChanges.put("Panel.background",LIGHT_PINK);

    }

    // effects: sets up the option panel
    private void optionPane() {
        Object[] textLabels = {"Question:", questionArea, "Answer:", answerArea};
        JButton[] buttons = { confirm, cancel };

        int result = JOptionPane.showOptionDialog(createDeckPanel, textLabels, "Add Card",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, icon,
                buttons, buttons[0]);

        if (result == JOptionPane.YES_OPTION) {
            System.out.println(questionArea.getText() + " : " + answerArea.getText());
        } else {
            System.out.println("Canceled");
        }
    }

    // effects: creates attributes of JOptionPanel buttons
    public static void makeJOptionButtons(JButton button) {
        try {
            Font smallCalibri = new Font("Calibri", Font.BOLD, 25);
            Image img = ImageIO.read(new File("src/images/button.png"));
            Image scaledImg = img.getScaledInstance(368/2, 111/2, java.awt.Image.SCALE_SMOOTH);
            ImageIcon buttonImage = new ImageIcon(scaledImg);
            button.setIcon(buttonImage);
            button.setFont(smallCalibri);
            buttonHelper(button);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // effects: create the confirm and cancel button
    private void makeCButtons() {
        confirm = new JButton("Confirm");
        makeJOptionButtons(confirm);
        cancel = new JButton("Cancel");
        makeJOptionButtons(cancel);
    }

    // getters
    public JButton getConfirm() {
        return this.confirm;
    }

    public JButton getCancel() {
        return this.cancel;
    }

    private void charCountCheck(FocusEvent e, String text) {
        question = text;
        answer = text;
        JTextArea textArea = (JTextArea) e.getSource();
        if (textArea.getText().length() > 140) {
            int charOver = textArea.getText().length() - 140;
            String message = "You are " + charOver + " characters\nover the limit!";
            JOptionPane.showMessageDialog(addCardPanel, message, "Character Max Reached",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            text = textArea.getText();
            System.out.println(text); // delete later
        }
    }

    @Override
    // saves the title entered
    public void focusLost(FocusEvent e) {
        if (e.getSource() == questionArea) {
            charCountCheck(e, question);
        } else if (e.getSource() == answerArea) {
            charCountCheck(e, answer);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}