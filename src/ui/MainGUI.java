package ui;
import model.Deck;
import model.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static ui.HelperMethods.*;

public class MainGUI extends JPanel {

    public final static int WIDTH = 1500;
    public final static int HEIGHT = 1000;
    public final static Font CONSOLAS = new Font("Consolas", Font.BOLD, 37);
    public final static Color BRIGHT_PINK = new Color(253, 27, 96);
    public final static Color LIGHT_PINK = new Color(254, 228, 227);
    public final static Color BRIGHT_TURNIPS = new Color(255, 168, 183);
    public final static String ENTER_TITLE = "Type your title...";

    private JFrame frame;
    private JPanel cards;
    private JPanel titleOptionsPanel;
    private LoadDeck load;
    private JButton createDeck;
    private JButton loadDeck;
    private CreateCards create;
    private Deck deck;
    private FindFile find;
    private Theme theme;
    private CardLayout cl;

    public MainGUI() {
        deck = new Deck(ENTER_TITLE);
        makeFrame();
        layers();
        frame.pack();
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // makes the windows icon
    private void prettyFrame(JFrame frame) {
        Image icon = null;
        try {
            icon = ImageIO.read(new File("src/images/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.setIconImage(icon);
        frame.setSize(200, 200);
    }

    // effects: the entire GUI, essentially
    private void makeFrame() {
        frame = new JFrame("Cutsey Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prettyFrame(frame);
        frame.getContentPane().setLayout(new CardLayout());
    }

    // effects: organize the layers of the GUI
    private void layers() {

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cl = (CardLayout) (cards.getLayout());
        displayTitleOptions();
        cards.add(titleOptionsPanel, "create warning");

        create = new CreateCards(deck, frame);
        cards.add(create.createDeckPanel, "create deck");

        load = new LoadDeck(deck);
        cards.add(load.loadDeckPanel, "loading");

        find = new FindFile(deck, frame, load, create, cards);
        cards.add(find.findFilePanel, "find file panel");

        theme = new Theme();
        cards.add(theme.themeSettingPanel, "theme setting");

        Container pane = frame.getContentPane();
        pane.add(cards, BorderLayout.CENTER);
    }

    // effects: shows the Create Deck and Load Deck option
    private void displayTitleOptions() {
        titleOptionsPanel = new JPanel();
        titleOptionsPanel.setBackground(LIGHT_PINK);
        titleOptionsPanel.setLayout(new BoxLayout(titleOptionsPanel, BoxLayout.Y_AXIS));
        Icon icon = new ImageIcon("src/images/titleGif.gif");
        JLabel title = new JLabel(icon);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleOptionsPanel.add(title);
        titleButtons(titleOptionsPanel);
    }

    //effects: displays the menu buttons
    public void titleButtons(JPanel panel) {

        try {
            BufferedImage image = ImageIO.read(new File("src/images/button.png"));
            ImageIcon buttonImage = new ImageIcon(image);
            createDeck = new JButton("Create Deck", buttonImage);
            createDeck.addActionListener(e -> cl.show(cards, "create deck"));
            loadDeck = new JButton("Load Deck", buttonImage);
            loadDeck.addActionListener(e -> {
                if (find.hasLoadable()) {
                    cl.show(cards, "find file panel");
                } else {
                    createNoButtonJOption(titleOptionsPanel, "No deck can be loaded!",
                            "Not to be mean...but...", "src/images/sorry.png", 120, 120);
                }
            });
            buttonHelper(createDeck);
            createDeck.setFont(CONSOLAS);
            buttonHelper(loadDeck);
            loadDeck.setFont(CONSOLAS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        panel.add(createDeck);
        panel.add(Box.createVerticalStrut(45));
        panel.add(loadDeck);
        createDeck.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadDeck.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
