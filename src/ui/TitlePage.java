package ui;

import model.Card;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TitlePage extends JPanel {

    public final static int WIDTH = 1500;
    public final static int HEIGHT = 1000;
    public final static Font CONSOLAS = new Font("Consolas", Font.BOLD, 37);
    public final static Color BRIGHT_PINK = new Color(253, 27,96);
    public final static Color LIGHT_PINK = new Color(254,228,227);


    private JFrame frame;
    private JPanel cards;
    private JPanel titleOptionsPanel;
    private LoadDeck load;
    private JButton createDeck;
    private JButton loadDeck;
    private TitlePageActionListener listen;
    private CreateCards create;

    public TitlePage() {
        makeFrame();
        layers();
        frame.pack();
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // effects: the entire GUI, essentially
    private void makeFrame() {

        // credits: https://stackoverflow.com/questions/2482971/how-to-change-the-color-of-titlebar-in-jframe
        UIDefaults uiDefaults = UIManager.getDefaults();
        uiDefaults.put("activeCaption", new javax.swing.plaf.ColorUIResource(BRIGHT_PINK));
        uiDefaults.put("activeCaptionText", new javax.swing.plaf.ColorUIResource(Color.white));
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame = new JFrame("Cutsey Cards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Image icon = null;
        try {
            icon = ImageIO.read(new File("src/images/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setIconImage(icon);
        frame.setSize(200,200);

        frame.getContentPane().setLayout(new CardLayout());
    }

    // effects: organize the layers of the GUI
    private void layers() {
        listen = new TitlePageActionListener();

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        displayTitleOptions();
        cards.add(titleOptionsPanel, "create warning");

        create = new CreateCards();
        create.displayCreateDeck();
//        frame.getContentPane().add(create.mainPanel);
        cards.add(create.mainPanel, "create deck");

        load = new LoadDeck();
        load.displayLoadDeck();
//        frame.getContentPane().add(load.loadDeckPanel);
        cards.add(load.loadDeckPanel, "load deck");

        Container pane = frame.getContentPane();
        pane.add(cards, BorderLayout.CENTER);
    }


    // effects: shows the Create Deck and Load Deck option
    private void displayTitleOptions() {
        titleOptionsPanel = new JPanel();
        titleOptionsPanel.setBackground(LIGHT_PINK);
        titleOptionsPanel.setLayout(new BoxLayout(titleOptionsPanel, BoxLayout.Y_AXIS));
//        frame.getContentPane().add(titleOptionsPanel);
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


    // modifies: this
    // effects: sets up and aligns the title page buttons
    public static void buttonHelper(JButton button) {
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(BRIGHT_PINK);
    }

    // getters
    public CardLayout getCl() {
        return (CardLayout) cards.getLayout();
    }

    public JPanel getCards() {
        return cards;
    }

    public TitlePageActionListener getAl() {
        return listen;
    }

    class TitlePageActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) (cards.getLayout());
            String cmd = e.getActionCommand();
            if (cmd.equals("create deck")) {
                cl.show (cards, "create deck");
            } else if (cmd.equals("load deck")) {
                cl.show(cards, "load deck");
                JOptionPane.showMessageDialog(load.loadDeckPanel, "Your cards are loading in!",
                        "Patience, patience!", JOptionPane.INFORMATION_MESSAGE);
            } else if (cmd.equals("create warning")) {
                cl.first(cards);
            }
        }
    }
}
