package ui;

import model.Deck;
import model.JsonReader;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static javax.swing.BorderFactory.createEmptyBorder;
import static ui.CreateCards.CALIBRI_BOLD;
import static ui.HelperMethods.*;
import static ui.MainGUI.*;
import static ui.MainGUI.LIGHT_PINK;

public class FindFile {

    private CardLayout cl;
    private JPanel cards;
    private CreateCards create;
    private Deck deck;
    protected JPanel findFilePanel;
    private JTextArea scrollTextPanel;
    private JScrollPane scrollPane;
    private JTextField textField;

    public FindFile(Deck deck, CreateCards create, JPanel cards) {
        this.deck = deck;
        this.create = create;
        this.cards = cards;
        cl = (CardLayout) (cards.getLayout());
        setUpFindPanel();
    }

    // effects: groups all the components together
    // includes: icon, scroll, buttons, text field
    private void setUpFindPanel() {

        findFilePanel = new JPanel(new MigLayout("align 50% 50%"));
        findFilePanel.setBackground(LIGHT_PINK);

        // credits: https://visualpharm.com/free-icons/live%20folder-595b40b65ba036ed117d2f2a
        Icon icon = new ImageIcon("src/images/folder.png");
        JLabel loadingGif = new JLabel(icon);
        loadingGif.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scrollLabel = new JLabel("Save Files Identified", JLabel.LEFT);
        scrollLabel.setFont(CONSOLAS);
        scrollLabel.setForeground(BRIGHT_PINK);

        setUpScroll();

        findFilePanel.add(loadingGif, "wrap, center");
        findFilePanel.add(scrollLabel, "wrap, center");
        findFilePanel.add(scrollPane, "wrap, center");
        setUpTextBox();
    }

    // effects: sets up the scroll that displays all pre-existing json files found
    private void setUpScroll() {
        // init the panel and scroll as well as functional/physical attributes
        scrollTextPanel = new JTextArea();
        scrollTextPanel.setOpaque(false);
        scrollTextPanel.setFont(CONSOLAS);
        scrollTextPanel.setEditable(false);
        scrollTextPanel.setBorder(new CompoundBorder(scrollTextPanel.getBorder(),
                new EmptyBorder(30, 30, 30, 30)));

        scrollPane = new JScrollPane(scrollTextPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setBackground(new Color(255, 194, 199));

        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 700, HEIGHT - 700));
        scrollPane.setBorder(createEmptyBorder());
        getFileNamesFound();

        scrollPane.setViewport(backgroundViewPort("src/images/pinkPaintBackground.jpg"));
        scrollPane.setViewportView(scrollTextPanel);
    }

    // effects: searches through data folder and prints out the names
    // of each file ending in .json every 5 seconds
    private void getFileNamesFound() {
        // credits: https://stackoverflow.com/questions/12908412/print-hello-world-every-x-seconds
        Runnable run = () -> {
            File f = new File("data");
            FilenameFilter filter = (f1, name) -> name.endsWith(".json");
            ArrayList<String> noJson = new ArrayList<>();
            for (String name : Objects.requireNonNull(f.list(filter))) {
                String noJsonName = name.substring(0, name.lastIndexOf('.'));
                noJson.add("\u2665 " + noJsonName);
            }
            String files = String.join("\n", noJson);
            scrollTextPanel.setText(files);
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(run, 0, 2, TimeUnit.SECONDS);
    }

    // effects: checks if there are any files in the data folder
    // that can be loaded
    protected Boolean hasLoadable() {
        return scrollTextPanel.getText().length() != 0;
    }

    // effects: creates the text box to select deck desired as well as
    // confirmation search button
    private void setUpTextBox() {
        JPanel holdTextComponents = new JPanel(new MigLayout());
        Font calItalic = new Font("Calibri", Font.ITALIC, 20);
        textField = new JTextField("Enter a file name...");
        textField.setColumns(25);
        textField.setFont(calItalic);
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                JTextField tf = (JTextField) e.getSource();
                tf.setText("");
            }
            @Override
            public void focusLost(FocusEvent e) {
            }
        });

        JLabel nameLabel = new JLabel("File Name:");
        nameLabel.setFont(CALIBRI_BOLD);
        nameLabel.setForeground(BRIGHT_PINK);
        JButton findFileButton = new JButton("Find");
        makeJOptionButtons(findFileButton);
        findFileButton.addActionListener(e -> {
            if (loadDeck()) {
                cl.show(cards, "loading");
            } else {
                // credits: Leisurely Pig Line Sticker
                createNoButtonJOption(create.createDeckPanel, "No deck with that name exists!",
                        "Not to be mean...but...", "src/images/sorry.png", 120, 120);
            }
        });

        holdTextComponents.add(nameLabel, "wrap, gapy 15");
        holdTextComponents.add(textField);
        holdTextComponents.add(findFileButton);
        JButton menuButton = createMenuButton();
        menuButton.addActionListener(e -> cl.first(cards));
        holdTextComponents.add(menuButton);
        findFilePanel.add(holdTextComponents);
    }

    // effects: loads up the previously made flashcards
    // inspired by JsonSerializationDemo
    // effects: loads the flashcards from file
    private Boolean loadDeck() {
        try {
            JsonReader jsonReader = new JsonReader(getSource());
            deck = jsonReader.read();
            System.out.println("Loaded " + deck.getTitle() + " from " + getSource());
            return true;
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + getSource());
            return false;
        }
    }

    // getters
    public String getSource() {
        return "./data/" + textField.getText() + ".json";
    }

    public String getTitle() {
        return textField.getText();
    }

    public Deck getFindDeck() {
        return this.deck;
    }
}
