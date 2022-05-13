package ui;

import model.Card;
import model.Deck;
import model.JsonWriter;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.*;
import java.util.Map;

import static javax.swing.BorderFactory.createEmptyBorder;
import static model.Card.NUM_ATTEMPTS;
import static ui.HelperMethods.*;
import static ui.MainGUI.*;

public class CreateCards {

    public final static Font CALIBRI_BOLD = new Font("Calibri", Font.BOLD, 30);
    public final static int CHAR_LIMIT = 189;
    public final static String REGEX_TITLE = "[a-zA-Z0-9 ._]*";

    protected JPanel createDeckPanel;
    protected Deck deck;
    private JPanel scrollArea;
    private JPanel header;
    protected JTextArea title;
    private String userTitle;
    private JButton menuButton;
    private JButton saveButton;
    private JButton addButton;
    private JButton okay;
    private JButton nope;
    private JScrollPane scrollPane;
    private CuteFocusListener fListen;

    private JPanel addCardPanel;

    private JButton confirm;
    private JButton cancel;
    private JButton clear;
    private JButton startButton;

    private String question;
    private String answer;

    private RoundJTextArea questionArea;
    private RoundJTextArea answerArea;

    private String source;
    private JsonWriter jsonWriter;

    public CreateCards(Deck deck) {
        fListen = new CuteFocusListener();
        userTitle = "";
        this.deck = deck;
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
        startButton();
    }

    // creates the static header that doesn't move with scroll
    private void setUpHeader() {

        header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        header.add(displayTitle());
        menuButton();
        createCard();
        saveButton();
        header.setBackground(LIGHT_PINK);

        createDeckPanel.add(header, BorderLayout.NORTH);
    }

    // effects: creates the vertical scroll pane to look at created cards
    private void setUpScrollPane() {

        // init the panel and scroll as well as functional/physical attributes
        scrollArea = new JPanel(new BorderLayout(20, 10));
        scrollArea.setOpaque(false);
        scrollPane = new JScrollPane(scrollArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 90, HEIGHT - 230));
        scrollPane.setBorder(createEmptyBorder());

        scrollPane.setViewport(backgroundViewPort("src/images/createCardBackground.png"));
        scrollPane.setViewportView(scrollArea);
        createDeckPanel.add(scrollPane, BorderLayout.CENTER);
    }

    // effects: displays an editable title for the new deck
    private JTextArea displayTitle() {
        Font calItalic = new Font("Calibri", Font.ITALIC, 40);
        Map attributes = calItalic.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        title = new RoundJTextArea(deck.getTitle());

        title.addFocusListener(fListen);
        title.setColumns(20);
        title.setFont(calItalic.deriveFont(attributes));

        textAreaHelper(title);
        return title;
    }

    // effects: common code among all three add card buttons
    private JButton createCardsButtonHelper(JButton button, String source, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(source);
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);
        button.setIcon(imageIcon);
        button.setFont(CALIBRI_BOLD);
        buttonHelper(button);
        return button;
    }

    // effects: brings you back to the main title page,
    // congratulates/prompts you to save your spot/progress
    private void menuButton() {
        menuButton = createMenuButton();
        menuButton.setBorder(BorderFactory.createCompoundBorder(
                menuButton.getBorder(),
                createEmptyBorder(0, 30, 0, 0)));
        header.add(menuButton);
    }

    // effects: constructs a button to save the deck of flashcards
    private void saveButton() {
        saveButton = new JButton("Save");
        makeJOptionButtons(saveButton);
        header.add(saveButton);
    }

    // effects: adds a new card to the deck
    private void createCard() {
        addButton = new JButton("Add Card");
        makeJOptionButtons(addButton);
        header.add(addButton);
        frameChanges();
    }

    // effects: opens up the options to start studying
    // includes theme, shuffle, starred only
    private void startButton() {
        startButton = new JButton("Start");
        startButton.setBorder(BorderFactory.createCompoundBorder(
                startButton.getBorder(),
                createEmptyBorder(0, 1200, 0, 0)));
        createCardsButtonHelper(startButton, "src/images/start.png",140,120);
        scrollArea.add(startButton, BorderLayout.SOUTH);
    }

    // effects: a pop up message asking if user would like to save their cards
    // before exiting to the menu
    protected void menuWarnUser() {

        JButton[] buttons = {okay, nope};

        if (!deck.getFlashCards().isEmpty()) {

            File f = new File("data");
            FilenameFilter filter = (f1, name) -> name.equals(userTitle + ".json");
            if (f.list(filter).length > 0) {
                JOptionPane.showOptionDialog(createDeckPanel, "Save and overwrite a file?",
                        "Menu Warning",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        createSmallIcon("src/images/save.png"),
                        buttons, buttons[0]);
            } else {
                JOptionPane.showOptionDialog(createDeckPanel, "Would you like to save your cards?",
                        "Menu Warning", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        createSmallIcon("src/images/save.png"), buttons, buttons[0]);
            }
        }
    }

    // getters
    public JButton getMenuButton () {
        return this.menuButton;
    }

    public JButton getAddCardButton () {
        return this.addButton;
    }

    public JButton getSaveButton () {
        return this.saveButton;
    }

    public JButton getAbsolutelyButton() {
        return this.okay;
    }

    public JButton getNopeButton () {
        return this.nope;
    }

    public String getUserTitle() {
        return this.userTitle;
    }

    public void setUserTitle(String newTitle) {
        this.userTitle = newTitle;
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


    // TODO SPLIT INTO SEPARATE CLASS LATER
    // the pop up panel to add new cards

    private void decoratePanel() {
        addCardPanel = new JPanel(new GridBagLayout());

        questionArea = new RoundJTextArea("Type your question here...\n(" + CHAR_LIMIT + " characters max)");
        textAreaHelper(questionArea);
        questionArea.setColumns(20);
        questionArea.setRows(7);
        questionArea.setFont(new Font("Calibri", Font.BOLD, 25));
        questionArea.addFocusListener(fListen);

        answerArea = new RoundJTextArea("Type your answer here...\n(" + CHAR_LIMIT + " characters max)");
        answerArea.setColumns(20);
        answerArea.setRows(7);
        textAreaHelper(answerArea);
        answerArea.setFont(new Font("Calibri", Font.BOLD, 25));
        answerArea.addFocusListener(fListen);

        addCardPanel.add(questionArea);
        addCardPanel.add(answerArea);
    }

    // effects: sets up the option panel
    protected void optionPane() {
        Object[] textLabels = {"Question:", questionArea, "Answer:", answerArea};
        JButton[] buttons = {confirm, cancel, clear};

        JOptionPane.showOptionDialog(createDeckPanel, textLabels, "Add Card",
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, createSmallIcon("src/images/turnips.png"),
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

        okay = new JButton("Okay!");
        makeJOptionButtons(okay);
        nope = new JButton("Nope.");
        makeJOptionButtons(nope);
    }

    // effects: checks if the answer/question text is < 189 char
    private void charCountCheck(FocusEvent e) {
        JTextArea textArea = (JTextArea) e.getSource();
        if (textArea.getText().length() > CHAR_LIMIT) {
            int charOver = textArea.getText().length() - CHAR_LIMIT;
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
        int charOver = questionArea.getText().length() - CHAR_LIMIT;
        if (questionArea.getText().length() > CHAR_LIMIT) {
            String message = "Your question is " + charOver + " characters\nover the limit!" +
                    "\nPlease fix your question before creating your card.";
            createNoButtonJOption(addCardPanel, message,
                    "Not to be mean...but...", "src/images/knife.png");
        } else if (answerArea.getText().length() > CHAR_LIMIT) {
            String message = "Your answer is " + charOver + " characters\nover the limit!" +
                    "\nPlease fix your answer before creating your card.";
            createNoButtonJOption(addCardPanel, message,
                    "Not to be mean...but...", "src/images/knife.png");
        } else if (questionArea.getText().length() == 0 || answerArea.getText().length() == 0) {
            createNoButtonJOption(addCardPanel, "You may not have empty questions or answers.",
                    "Not to be mean...but...", "src/images/knife.png");
        } else {
            question = questionArea.getText();
            answer = answerArea.getText();
            Card newCard = new Card(question, answer, false, false, NUM_ATTEMPTS, true);
            deck.addFlashCard(newCard);
        }
    }

    // effects: clear all text in the question and answer area
    protected void clear() {
        questionArea.setText("");
        answerArea.setText("");
    }

    // credits: JsonSerializationDemo CPSC 210
    // effects: saves the new deck of flashcards to file
    protected void saveDeck() {
        source = "./data/" + userTitle + ".json";

        try {
            Path path = Paths.get(source);
            if (Files.exists(path)) {
                Files.delete(path);
            }
            Files.createFile(path);
            jsonWriter = new JsonWriter(source);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            jsonWriter.open();
            jsonWriter.write(deck);
            jsonWriter.close();
            System.out.println("Saved " + userTitle + " to " + source);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + source);
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
                if (titleTextArea.getText().matches(REGEX_TITLE)) {
                    userTitle = titleTextArea.getText();
                    deck.setTitle(userTitle);
                }
            }
            if (e.getSource() == questionArea) {
                charCountCheck(e);
            } else if (e.getSource() == answerArea) {
                charCountCheck(e);
            }
        }
    }
}
