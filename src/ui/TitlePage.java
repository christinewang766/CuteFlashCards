package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TitlePage extends JPanel {

    private int WIDTH = 1500;
    private int HEIGHT = 1000;
    private Font comicSans = new Font("Comic Sans MS", Font.BOLD, 30);
    public final static Color brightPink = new Color(253, 27,96);
    public final static Color lightPink = new Color(254,228,227);


    private JFrame frame;
    private JPanel cards;
    private JPanel titleOptionsPanel;
    private LoadDeck load;
    private JButton createDeck;
    private JButton loadDeck;
    private SpecialActionListenter listen;

    public TitlePage() {
        makeFrame();
        frame.pack();
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // effects: the entire GUI, essentially
    private void makeFrame() {

        listen = new SpecialActionListenter();

        frame = new JFrame();
        frame.getContentPane().setLayout(new CardLayout());

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        displayTitleOptions();
        cards.add(titleOptionsPanel);

        CreateCards create = new CreateCards();
        create.displayCreateDeck();
        frame.getContentPane().add(create.createDeckPanel);
        cards.add(create.createDeckPanel, "create deck");

        load = new LoadDeck();
        load.displayLoadDeck();
        frame.getContentPane().add(load.loadDeckPanel);
        cards.add(load.loadDeckPanel, "load deck");

        Container pane = frame.getContentPane();
        pane.add(cards, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cutsey Cards");
    }


    // effects: shows the Create Deck and Load Deck option
    private void displayTitleOptions() {
        titleOptionsPanel = new JPanel();
        titleOptionsPanel.setBackground(lightPink);
        titleOptionsPanel.setLayout(new BoxLayout(titleOptionsPanel, BoxLayout.Y_AXIS));
        frame.getContentPane().add(titleOptionsPanel);
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
            buttonHelper(loadDeck);
        } catch (IOException e) {
            e.printStackTrace();
        }

        createDeck.setActionCommand("create deck");
        createDeck.addActionListener(listen);

        loadDeck.setActionCommand("load deck");
        loadDeck.addActionListener(listen);

        panel.add(createDeck);
        panel.add(Box.createVerticalStrut(35));
        panel.add(loadDeck);
        createDeck.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadDeck.setAlignmentX(Component.CENTER_ALIGNMENT);
    }


    // modifies: this
    // effects: sets up and aligns the title page buttons
    private void buttonHelper(JButton button) {
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.CENTER);
        button.setContentAreaFilled(false);
        button.setFont(comicSans);
        button.setBorderPainted(false);
        button.setForeground(brightPink);
    }

    class SpecialActionListenter implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) (cards.getLayout());
            String cmd = e.getActionCommand();
            if (cmd.equals("create deck")) {
                cl.show (cards, "create deck");
                new CreateCards();
            } else if (cmd.equals("load deck")) {
                cl.show(cards, "load deck");
                JOptionPane.showMessageDialog(load.loadDeckPanel, "Your cards are loading in!",
                        "Patience, patience!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // getters
    public JFrame getFrame() {
        return this.frame;
    }

}
