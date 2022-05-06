package ui;

import net.miginfocom.swing.MigLayout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FlashCardApp extends JPanel implements ActionListener {

    private int WIDTH = 1500;
    private int HEIGHT = 1000;
    private Font comicSans = new Font("Comic Sans MS", Font.BOLD, 30);

    private JFrame frame;
    private Container titlePageContainer;
    private JLabel label;
    private JPanel panel;

    public FlashCardApp() {
        titleBackGround();
    }

    private void titleBackGround() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Cutsey Cards");

        try {
            frame.setContentPane(new BackgroundPanel(ImageIO.read(new File("src/images/title.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        addButtons(frame.getContentPane());
//        frame.add(panel,BorderLayout.CENTER);

        frame.pack();
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setVisible(true);
    }

//    // effects: makes title page button for new deck of cards
//    private void buttonHelper(JButton button, Font font) {
//        Color lightPink = new Color(250, 244, 246);
//        Color brightPink = new Color(235, 27,96);
//        button.setFont(font);
//        button.setOpaque(false);
//        button.setContentAreaFilled(false);
//        button.setBorderPainted(false);
//        panel.setBackground(lightPink);
//        button.setForeground(brightPink);
//        button.addActionListener(this);
//    }

//    private void titlePageButtons() {
//        panel = new JPanel();
////        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
//        panel.setLayout(new MigLayout());
//
//        JButton createDeck = new JButton("Create Deck");
//        panel.add(createDeck, "wrap");
//        buttonHelper(createDeck, comicSans);
//
//
//        JButton loadDeck = new JButton("Load Deck");
//        panel.add(loadDeck);
//        buttonHelper(loadDeck, comicSans);
//    }

    // modifies: this
    // effects: sets up and aligns the title page buttons
    public JButton buttonHelper(GridBagConstraints constraint, String text, Container panel) {
        JButton button;
        Color brightPink = new Color(235, 27,96);

        button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setFont(comicSans);
        button.setBorderPainted(false);
        button.setForeground(brightPink);
        panel.add(button, constraint);

        return button;
    }

    // modifies: this
    // effects: creates and groups buttons together and loads up the title image
    public Container addButtons(Container panel) {

        GridBagConstraints c = new GridBagConstraints();
        panel.setLayout(new GridBagLayout());

        c.insets = new Insets(265,0,0,0);  //top padding
        c.gridx = 1;       //aligned with button 2
        c.gridy = 2;       //third row        buttonHelper(c, "Create Deck", panel).addActionListener(this);

        buttonHelper(c, "Create Deck", panel).addActionListener(this);

        c.insets = new Insets(550,0,0,0);  //top padding
        buttonHelper(c, "Load Deck", panel).addActionListener(this);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // draws background
    static class BackgroundPanel extends JPanel {
        BufferedImage background;
        public BackgroundPanel(BufferedImage image) throws IOException {
            background = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background, 0, 0, this);
        }
    }

}
