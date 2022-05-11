package ui;

import model.Card;
import model.Deck;
import model.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static javax.swing.BorderFactory.createEmptyBorder;
import static model.Card.NUM_ATTEMPTS;
import static ui.HelperMethods.*;
import static ui.MainGUI.*;

public class CreateCards {

    public final static Font CALIBRI_ITALIC = new Font("Calibri", Font.ITALIC, 40);
    public final static Font CALIBRI_BOLD = new Font("Calibri", Font.BOLD, 30);
    public final static String ENTER_TITLE = "Type your title...";

    protected JPanel createDeckPanel;
    private JsonWriter jsonWriter;
    protected Deck deck;
    private JPanel scrollArea;
    private JPanel header;
    private JTextArea title;
    private String userTitle;
    private JButton addCardButton;
    private JButton menuButton;
    private JButton saveButton;
    private JScrollPane scrollPane;
    private Card newCard;
    private CuteFocusListener fListen;

    private JPanel addCardPanel;

    private JButton confirm;
    private JButton cancel;
    private JButton clear;
    private Icon icon;

    private String question;
    private String answer;

    private RoundJTextArea questionArea;
    private RoundJTextArea answerArea;

    public CreateCards() {
        fListen = new CuteFocusListener();
        jsonWriter = new JsonWriter(JSON_STORE);

        userTitle = "Add a title";
        deck = new Deck(userTitle);
        newCard = null;
        displayCreateDeck();

        question = "";
        answer = "";
        makeCButtons();
        decoratePanel();
    }

    // effects: shows the flashcards being made
    protected void displayCreateDeck() {
        createDeckPanel = new JPanel(new FlowLayout((int) Component.CENTER_ALIGNMENT, 40, 40));
        createDeckPanel.setBackground(LIGHT_PINK);
        setUpHeader();
        setUpScrollPane();
    }

    // creates the static header that doesn't move with scroll
    private void setUpHeader() {

        header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        header.add(displayTitle());
        menuButton(header);
        createCard(header);
        saveButton(header);
        header.setBackground(LIGHT_PINK);

        createDeckPanel.add(header, BorderLayout.NORTH);
    }

    // effects: constructs a button to save the deck of flashcards
    private void saveButton(JPanel panel) {
        ImageIcon imageIcon = new ImageIcon("src/images/button.png");
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(184, 70, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);

        saveButton = new JButton("Save", imageIcon);
        saveButton.setFont(CALIBRI_BOLD);
        buttonHelper(saveButton);
        panel.add(saveButton);
    }

    // effects: creates the vertical scroll pane to look at created cards
    private void setUpScrollPane() {

        // init the panel and scroll as well as functional/physical attributes
        scrollArea = new JPanel(new BorderLayout(20, 10));
        scrollArea.setOpaque(false);
        scrollPane = new JScrollPane(scrollArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 100, HEIGHT - 240));
        scrollPane.setBorder(createEmptyBorder());

        // assigns a static background image
        ImageIcon imageIcon = new ImageIcon("src/images/createCardBackground.png");
        Image image = imageIcon.getImage();
        Image scaledImg = image.getScaledInstance(WIDTH, HEIGHT, java.awt.Image.SCALE_SMOOTH);

        // credit: https://coderanch.com/t/700884/java/static-background-scrollpane
        JViewport viewport = new JViewport() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.drawImage(scaledImg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        scrollPane.setViewport(viewport);
        scrollPane.setViewportView(scrollArea);
        createDeckPanel.add(scrollPane, BorderLayout.CENTER);
    }

    // effects: displays an editable title for the new deck
    private JTextArea displayTitle() {

        Map attributes = CALIBRI_ITALIC.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        userTitle = "";

        title = new RoundJTextArea("");
        title.addFocusListener(fListen);
        title.setText(ENTER_TITLE);
        title.setColumns(20);
        title.setFont(CALIBRI_ITALIC.deriveFont(attributes));

        textAreaHelper(title);
        return title;
    }

    // effects: brings you back to the main title page,
    // congratulates/prompts you to save your spot/progress
    public void menuButton(JPanel panel) {
        ImageIcon imageIcon = new ImageIcon("src/images/button.png");
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(184, 70, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);

        menuButton = new JButton("Menu", imageIcon);
        menuButton.setFont(CALIBRI_BOLD);
        buttonHelper(menuButton);
        menuButton.setBorder(BorderFactory.createCompoundBorder(
                menuButton.getBorder(),
                createEmptyBorder(0, 30, 0, 0)));
        panel.add(menuButton);
    }

    // effects: adds a new card to the deck
    private void createCard(JPanel panel) {
        ImageIcon imageIcon = new ImageIcon("src/images/button.png");
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(184, 70, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);

        addCardButton = new JButton("Add Card", imageIcon);
        addCardButton.setFont(CALIBRI_BOLD);
        buttonHelper(addCardButton);
        panel.add(addCardButton);
        frameChanges();
    }

    // effects: a pop up message asking if user would like to save their cards
    // before exiting to the menu
    void menuWarnUser() {
    }

    // credits: JsonSerializationDemo CPSC 210
    // effects: saves the new deck of flashcards to file
    protected void saveDeck() {
        try {
            jsonWriter.open();
            jsonWriter.write(deck);
            jsonWriter.close();
            System.out.println("Saved " + deck.getTitle() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // getters
    public JButton getMenuButton () {
        return this.menuButton;
    }

    public JButton getAddCardButton () {
        return this.addCardButton;
    }

    public JButton getSaveButton () {
        return this.saveButton;
    }

    public String getUserTitle () {
        return this.userTitle;
    }

    public Deck getDeck() {
        return this.deck;
    }

    // getters for Template
    public JButton getConfirmButton() {
        return this.confirm;
    }

    public JButton getCancelButton() {
        return this.cancel;
    }

    public JButton getClearButton() {
        return this.clear;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    private void decoratePanel() {
        addCardPanel = new JPanel(new GridBagLayout());

        questionArea = new RoundJTextArea("Type your question here...\n(140 characters max)");
        textAreaHelper(questionArea);
        questionArea.setColumns(20);
        questionArea.setRows(7);
        questionArea.setFont(new Font("Calibri", Font.BOLD, 25));
        questionArea.addFocusListener(fListen);

        answerArea = new RoundJTextArea("Type your answer here...\n(140 characters max)");
        answerArea.setColumns(20);
        answerArea.setRows(7);
        textAreaHelper(answerArea);
        answerArea.setFont(new Font("Calibri", Font.BOLD, 25));
        answerArea.addFocusListener(fListen);

        addCardPanel.add(questionArea);
        addCardPanel.add(answerArea);

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
    }

    // effects: sets up the option panel
    protected void optionPane() {
        Object[] textLabels = {"Question:", questionArea, "Answer:", answerArea};
        JButton[] buttons = {confirm, cancel};

        JOptionPane.showOptionDialog(createDeckPanel, textLabels, "Add Card",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, icon,
                buttons, buttons[0]);
    }

    // effects: create the confirm and cancel button
    private void makeCButtons() {
        confirm = new JButton("Confirm");
        makeJOptionButtons(confirm);
        cancel = new JButton("Cancel");
        makeJOptionButtons(cancel);
        clear = new JButton("Clear");
        makeJOptionButtons(clear);
    }

    // effects: checks if the answer/question text is < 140 char
    private void charCountCheck(FocusEvent e) {
        JTextArea textArea = (JTextArea) e.getSource();
        if (textArea.getText().length() > 140) {
            int charOver = textArea.getText().length() - 140;
            String message = "You are " + charOver + " characters\nover the limit!";
            createNoButtonJOption(addCardPanel, message, "Character Max Reached", "src/images/knife.png");
        } else {
            if (e.getSource() == questionArea) {
                question = textArea.getText();
            } else if (e.getSource() == answerArea) {
                answer = textArea.getText();
            }
        }
    }

    // effects: checks if both the question and answer text areas
    // satisfy criteria and prints out appropriate message if not
    protected void finalCharCheck() {
        int charOver = questionArea.getText().length() - 140;
        if (questionArea.getText().length() > 140) {
            String message = "Your question is " + charOver + " characters\nover the limit!" +
                    "\nPlease fix your question before creating your card.";
            createNoButtonJOption(addCardPanel, message,
                    "Not to be mean...but...", "src/images/knife.png");
        } else if (answerArea.getText().length() > 140) {
            String message = "Your answer is " + charOver + " characters\nover the limit!" +
                    "\nPlease fix your answer before creating your card.";
            createNoButtonJOption(addCardPanel, message,
                    "Not to be mean...but...", "src/images/knife.png");
        } else {
            question = questionArea.getText();
            answer = answerArea.getText();
            newCard = new Card(question, answer, false, false, NUM_ATTEMPTS, true);
            deck.addFlashCard(newCard);
        }
    }

    

    private class CuteFocusListener implements FocusListener {
        @Override
        // effects: none, irrelevant! (for now...)
        public void focusGained(FocusEvent e) {
        }

        @Override
        // saves the title entered
        public void focusLost(FocusEvent e) {
            if (e.getSource() == title) {
                JTextArea titleTextArea = (JTextArea) e.getSource();
                userTitle = titleTextArea.getText();
                deck.setTitle(userTitle);
                System.out.println(userTitle); // delete later
            }
            if (e.getSource() == questionArea) {
                charCountCheck(e);
            } else if (e.getSource() == answerArea) {
                charCountCheck(e);
            }
        }
    }
}