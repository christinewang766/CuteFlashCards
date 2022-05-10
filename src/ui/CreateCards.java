package ui;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static javax.swing.BorderFactory.createEmptyBorder;
import static ui.AddCardTemplate.makeJOptionButtons;
import static ui.MainGUI.*;

public class CreateCards implements FocusListener, ActionListener {

    public final static Font CALIBRI_ITALIC = new Font("Calibri", Font.ITALIC, 40);
    public final static Font CALIBRI_BOLD = new Font("Calibri", Font.BOLD, 30);
    public final static String ENTER_TITLE = "Type your title...";

    protected JPanel createDeckPanel;
    private JPanel scrollArea;
    private JPanel header;
    private JTextArea title;
    private String userTitle;
    private JButton addCardButton;
    private JButton menuButton;
    private JScrollPane scrollPane;

    public CreateCards() {
        displayCreateDeck();
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
        title.addFocusListener(this);
        title.setText(ENTER_TITLE);
        title.setColumns(20);
        title.setFont(CALIBRI_ITALIC.deriveFont(attributes));

        textAreaHelper(title);
        return title;
    }

    // effects: text area attributes
    public static void textAreaHelper(JTextArea text) {
        text.setForeground(BRIGHT_PINK);
        text.setBackground(new Color(255, 245, 245));
        text.setBorder(createEmptyBorder());
        text.setBorder(BorderFactory.createCompoundBorder(
                text.getBorder(),
                createEmptyBorder(15, 15, 15, 15)));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
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
    }

    protected void createAddCardPopUp() {
        try {
            // credit: Emekat http://pixelartmaker.com/art/ad1ae9084485dac
            Image img = ImageIO.read(new File("src/images/knife.png"));
            Image scaledImg = img.getScaledInstance(130, 140, java.awt.Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImg);

            JOptionPane.showOptionDialog(createDeckPanel,
                    "Please add a new title!", "Not to be mean...but...",
                    JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, icon, new Object[]{}, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        // effects: a pop up message asking if user would like to save their cards
        // before exiting to the menu
        void warnUser () {
        }

        @Override
        // effects: none, irrelevant! (for now...)
        public void focusGained (FocusEvent e){
        }

        @Override
        // saves the title entered
        public void focusLost (FocusEvent e){
            JTextArea titleTextArea = (JTextArea) e.getSource();
            userTitle = titleTextArea.getText();
            System.out.println(userTitle); // delete later
        }

        // getters
        public JButton getMenuButton () {
            return menuButton;
        }

        public JButton getAddCardButton () {
            return addCardButton;
        }

        public String getUserTitle () {
            return this.userTitle;
        }

        @Override
        public void actionPerformed (ActionEvent e){
        }
}