package ui;

import model.Card;
import model.Deck;
import model.JsonWriter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import static javax.swing.BorderFactory.createEmptyBorder;
import static model.Card.NUM_ATTEMPTS;
import static ui.HelperMethods.*;
import static ui.MainGUI.*;

public class CreateCards {

    public final static Font CALIBRI_BOLD = new Font("Calibri", Font.BOLD, 30);
    public final static int CHAR_LIMIT = 250;
    public final static String REGEX_TITLE = "[a-zA-Z0-9 ._]*";
    public static final String TYPE_QUESTION_HERE = "Type your question here...\n(" + CHAR_LIMIT + " characters max)";
    public static final String TYPE_ANSWER_HERE = "Type your answer here...\n(" + CHAR_LIMIT + " characters max)";
    private JFrame frame;

    protected JPanel createDeckPanel;
    protected Deck deck;
    protected JPanel scrollArea;
    private JScrollPane scrollPane;
    private JPanel header;
    protected JTextArea title;
    private String userTitle;
    private JButton menuButton;
    private JButton saveButton;
    private JButton addButton;
    private JButton okay;
    private JButton nope;
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

    private JsonWriter jsonWriter;

    public CreateCards(Deck deck, JFrame frame) {
        this.frame = frame;
        fListen = new CuteFocusListener();
        userTitle = "";
        this.deck = deck;
        display();

        question = "";
        answer = "";
        makeCButtons();
        decoratePanel();
    }

    // effects: shows the flashcards being made
    protected void display() {
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
        scrollArea = new JPanel(new MigLayout("wrap 2"));
        scrollArea.setOpaque(false);
        scrollPane = new JScrollPane(scrollArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 90, HEIGHT - 230));
        scrollPane.setBorder(createEmptyBorder());

        resetScrollPane();
    }

    protected void resetScrollPane() {
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
        saveButton.addActionListener(e -> {
            if (deck.getFlashCards().isEmpty()) {
                createNoButtonJOption(createDeckPanel, "There's no cards to save!",
                        "Not to be mean...but...", "src/images/save.png", 100, 100);
            } else if (userTitle.isEmpty() ||
                    userTitle == ENTER_TITLE) {
                createNoButtonJOption(createDeckPanel, "Please choose a new title!",
                        "Not to be mean...but...", "src/images/knife.png", 110, 110);
            } else if (!userTitle.matches(REGEX_TITLE)) {
                createNoButtonJOption(createDeckPanel, "Please make sure that the title only\n"
                                + "contains letters and numbers.", "Listen, Buddy...", "src/images/knife.png",
                        110, 110);
            } else {
                saveDeck();
            }
        });
        header.add(saveButton);
    }

    // effects: adds a new card to the deck
    private void createCard() {
        addButton = new JButton("Add Card");
        makeJOptionButtons(addButton);
        addButton.addActionListener(e -> {
            if (userTitle.equals(ENTER_TITLE) || userTitle.equals("")) {
                String message = "Please choose a new title\nand make sure it only\ncontains letters and numbers.";
                createNoButtonJOption(createDeckPanel, message,
                        "Not to be mean...but...", "src/images/knife.png", 110, 110);
            } else {
                optionPane();
            }
        });
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
        // TODO
        startButton.addActionListener(e -> {
            saveDeck();
        });
        createCardsButtonHelper(startButton, "src/images/start.png", 140, 120);
        scrollArea.add(startButton, "south, gapy 70");
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
                        createSmallIcon("src/images/save.png", 100, 100),
                        buttons, buttons[0]);
            } else {
                JOptionPane.showOptionDialog(createDeckPanel, "Would you like to save your cards?",
                        "Menu Warning", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        createSmallIcon("src/images/save.png", 100, 100), buttons, buttons[0]);
            }
        }
    }

    // TODO SPLIT INTO SEPARATE CLASS LATER
    // the pop up panel to add new cards

    protected void decoratePanel() {
        addCardPanel = new JPanel(new GridBagLayout());

        questionArea = new RoundJTextArea(TYPE_QUESTION_HERE);
        textAreaHelper(questionArea);
        questionArea.setColumns(25);
        questionArea.setRows(10);
        questionArea.setFont(new Font("Calibri", Font.BOLD, 25));
        questionArea.addFocusListener(fListen);

        answerArea = new RoundJTextArea(TYPE_ANSWER_HERE);
        answerArea.setColumns(25);
        answerArea.setRows(10);
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
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                createSmallIcon("src/images/turnips.png", 120, 120),
                buttons, buttons[0]);
    }

    // effects: create the confirm and cancel button
    protected void makeCButtons() {
        confirm = new JButton("Confirm");
        makeJOptionButtons(confirm);
        confirm.addActionListener(e -> {
            finalCharCheck();
            clearCardText();
        });
        cancel = new JButton("Cancel");
        makeJOptionButtons(cancel);
        cancel.addActionListener(e -> closeCurrentWindow());
        clear = new JButton("Clear");
        makeJOptionButtons(clear);
        clear.addActionListener(e -> clearCardText());

        okay = new JButton("Okay!");
        makeJOptionButtons(okay);
        okay.addActionListener(e -> {
            if (deck.getFlashCards().isEmpty()) {
                createNoButtonJOption(createDeckPanel, "There's no cards to save!",
                        "Not to be mean...but...", "src/images/save.png", 100, 100);
            } else if (userTitle.isEmpty() ||
                    userTitle == ENTER_TITLE) {
                createNoButtonJOption(createDeckPanel, "Please choose a new title!",
                        "Not to be mean...but...", "src/images/knife.png", 110, 110);
            } else if (!userTitle.matches(REGEX_TITLE)) {
                createNoButtonJOption(createDeckPanel, "Title may only contain \n"
                                + "letters and numbers.", "Listen, Buddy...", "src/images/knife.png",
                        110, 110);
            } else {
                saveDeck();
                closeCurrentWindow();
            }
        });
        nope = new JButton("Nope.");
        makeJOptionButtons(nope);
    }

    // effects: checks if the answer/question text is <= 250 char
    private void charCountCheck(FocusEvent e) {
        JTextArea textArea = (JTextArea) e.getSource();
        if (textArea.getText().length() > CHAR_LIMIT) {
            int charOver = textArea.getText().length() - CHAR_LIMIT;
            String message = "You are " + charOver + " characters\nover the limit!";
            createNoButtonJOption(addCardPanel, message, "Character Max Reached", "src/images/knife.png",
                    110, 110);
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
                    "Not to be mean...but...", "src/images/knife.png", 110, 110);
        } else if (answerArea.getText().length() > CHAR_LIMIT) {
            String message = "Your answer is " + charOver + " characters\nover the limit!" +
                    "\nPlease fix your answer before creating your card.";
            createNoButtonJOption(addCardPanel, message,
                    "Not to be mean...but...", "src/images/knife.png", 110, 110);
        } else if (questionArea.getText().length() == 0 || answerArea.getText().length() == 0) {
            createNoButtonJOption(addCardPanel, "You may not have empty questions or answers.",
                    "Not to be mean...but...", "src/images/knife.png", 110, 110);
        } else if (repeatQuestion()) {
        } else {
            addCard();
            displayCreatedCards();
        }
    }

    // effects: after passing the other checks, show added cards
    protected void displayCreatedCards() {
        clearScrollArea();
        int i;
        i = 0;
        for (Card card : deck.getFlashCards()) {
            EditPanel ep = new EditPanel(deck, card, this);
            ep.changeIndex(i);
            scrollArea.add(ep.showCard(),"center, gapy 20");
            i++;
        }
        resetScrollPane();
    }

    // effects: deletes the display of previous cards on screen
    protected void clearScrollArea() {
        for(Component c : scrollArea.getComponents()) {
            if (!(c instanceof JButton)) {
                scrollArea.remove(c);
            }
        }
    }

    // effects: checks if another card already has the same question
    private Boolean repeatQuestion() {
        for (Card card : deck.getCards()) {
            if (card.getQuestion().equals(questionArea.getText())) {
                createNoButtonJOption(addCardPanel, "Another card already has the same question!",
                        "Not to be mean...but...", "src/images/knife.png", 110, 110);
                return true;
            }
        }
        return false;
    }

    // effects: assigns the question and answer of the card
    // creates a new card and adds that to the deck's flashcards
    private void addCard() {
        question = questionArea.getText();
        answer = answerArea.getText();
        Card newCard = new Card(question, answer, false, false, NUM_ATTEMPTS, true);
        deck.addFlashCard(newCard);
        // assigns created flashcard deck as the deckCards
        // to preserve the original set before filtering
        deck.setCards(deck.getFlashCards());
    }

    // effects: clear all text in the question and answer area
    protected void clearCardText() {
        questionArea.setText("");
        answerArea.setText("");
    }

    // credits: JsonSerializationDemo CPSC 210
    // effects: saves the new deck of flashcards to file
    protected void saveDeck() {
        String source = "./data/" + userTitle + ".json";

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

    public JButton getNopeButton() {
        return this.nope;
    }

    public JButton getMenuButton() {
        return this.menuButton;
    }

    public RoundJTextArea getQuestionArea() {
        return this.questionArea;
    }

    public RoundJTextArea getAnswerArea() {
        return this.answerArea;
    }

    private class CuteFocusListener implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            if (e.getSource() == title) {
                title.setBorder(BorderFactory.createLineBorder(BRIGHT_PINK, 5));
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (e.getSource() == title) {
                Font calItalic = new Font("Calibri", Font.ITALIC, 40);
                Map attributes = calItalic.getAttributes();
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

                RoundJTextArea t = new RoundJTextArea(deck.getTitle());
                t.setColumns(20);
                t.setFont(calItalic.deriveFont(attributes));
                textAreaHelper(title);

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