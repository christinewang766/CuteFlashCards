package ui;

import model.Card;
import model.JsonReader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static model.Card.NUM_ATTEMPTS;
import static ui.CreateCards.ENTER_TITLE;
import static ui.HelperMethods.*;

public class MainGUI extends JPanel {

    public final static int WIDTH = 1500;
    public final static int HEIGHT = 1000;
    public final static Font CONSOLAS = new Font("Consolas", Font.BOLD, 37);
    public final static Color BRIGHT_PINK = new Color(253, 27, 96);
    public final static Color LIGHT_PINK = new Color(254, 228, 227);


    private JFrame frame;
    private JPanel cards;
    private JPanel titleOptionsPanel;
    private LoadDeck load;
    private JButton createDeck;
    private JButton loadDeck;
    private TitlePageActionListener listen;
    private CreateCards create;
    private JsonReader jsonReader;
    public static final String JSON_STORE = "./data/CuteFlashCards.json";

    public MainGUI() {
        jsonReader = new JsonReader(JSON_STORE);
        makeFrame();
        layers();
        frame.pack();
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // makes the windows title pink with icon
    private void prettyFrame(JFrame frame) {
        // credits: https://stackoverflow.com/questions/2482971/how-to-change-the-color-of-titlebar-in-jframe
        UIDefaults uiDefaults = UIManager.getDefaults();
        uiDefaults.put("activeCaption", new javax.swing.plaf.ColorUIResource(BRIGHT_PINK));
        uiDefaults.put("activeCaptionText", new javax.swing.plaf.ColorUIResource(Color.white));
        JFrame.setDefaultLookAndFeelDecorated(true);

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
        listen = new TitlePageActionListener();

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
//        displayTitleOptions();
//        cards.add(titleOptionsPanel, "create warning");
//
        create = new CreateCards();
        createActions();
        cards.add(create.createDeckPanel, "create deck");

//        load = new LoadDeck();
//        loadActions();
//        cards.add(load.loadDeckPanel, "load deck");

        Container pane = frame.getContentPane();
        pane.add(cards, BorderLayout.CENTER);
    }

    // effects: the methods and buttons that must be called
    // from the CreateCards class before main GUI can run it
    private void createActions() {
        JButton menu = create.getMenuButton();
        menu.setActionCommand("create warning");
        menu.addActionListener(listen);
        JButton addCard = create.getAddCardButton();
        addCard.setActionCommand("add card");
        addCard.addActionListener(listen);

        JButton save = create.getSaveButton();
        save.setActionCommand("save");
        save.addActionListener(listen);
        JButton confirm = create.getConfirmButton();
        confirm.setActionCommand("confirm");
        confirm.addActionListener(listen);
        JButton cancel = create.getCancelButton();
        cancel.setActionCommand("cancel");
        cancel.addActionListener(listen);
        JButton clear = create.getClearButton();
        clear.setActionCommand("clear");
        clear.addActionListener(listen);
    }

    // effects: the methods and buttons that must be called
    // from the LoadDeck class before main GUI can run it
    private void loadActions() {
        // TO DO
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
            loadDeck = new JButton("Load Deck", buttonImage);
            buttonHelper(createDeck);
            createDeck.setFont(CONSOLAS);
            buttonHelper(loadDeck);
            loadDeck.setFont(CONSOLAS);
        } catch (IOException e) {
            e.printStackTrace();
        }

        createDeck.setActionCommand("create deck");
        createDeck.addActionListener(listen);

        loadDeck.setActionCommand("load deck");
        loadDeck.addActionListener(listen);

        panel.add(createDeck);
        panel.add(Box.createVerticalStrut(45));
        panel.add(loadDeck);
        createDeck.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadDeck.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    class TitlePageActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) (cards.getLayout());
            String cmd = e.getActionCommand();
            if (cmd.equals("create deck")) {
                cl.show(cards, "create deck");

            } else if (cmd.equals("load deck")) {
                cl.show(cards, "load deck");
                // TO DO MESSAGE
                JOptionPane.showMessageDialog(load.loadDeckPanel, "Your cards are loading in!",
                        "Patience, patience!", JOptionPane.INFORMATION_MESSAGE);

            } else if (cmd.equals("create warning")) {
                create.menuWarnUser();
                cl.first(cards);

            } else if (cmd.equals("save")) {
                if (create.getDeck().getFlashCards().isEmpty()) {
                    createNoButtonJOption(create.createDeckPanel, "There's no cards to save!",
                            "Not to be mean...but...", "src/images/save.png");
                } else if (create.getUserTitle() == "Add a title" ||
                        create.getUserTitle() == ENTER_TITLE) {
                    createNoButtonJOption(create.createDeckPanel, "Please choose a new title!",
                            "Not to be mean...but...", "src/images/knife.png");
                }
                create.saveDeck();

            } else if (cmd.equals("add card")) {
                if (create.getUserTitle().equals(ENTER_TITLE) || create.getUserTitle().equals("")) {
                    createNoButtonJOption(create.createDeckPanel, "Please choose a new title!",
                            "Not to be mean...but...", "src/images/knife.png");
                } else {
                    create.optionPane();
                }
            } else if (cmd.equals("confirm")) {
                create.finalCharCheck();
                System.out.println(create.getQuestion() + " : " + create.getAnswer());
            } else if (cmd.equals("cancel")) {
                // credits: https://stackoverflow.com/questions/18105598/closing-a-joptionpane-programmatically
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    if (window instanceof JDialog) {
                        JDialog dialog = (JDialog) window;
                        if (dialog.getContentPane().getComponentCount() == 1
                                && dialog.getContentPane().getComponent(0) instanceof JOptionPane) {
                            dialog.dispose();
                        }
                    }
                }
            }
        }
    }
}
