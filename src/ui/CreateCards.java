package ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.font.TextAttribute;
import java.awt.geom.RoundRectangle2D;
import java.util.Map;

import static javax.swing.BorderFactory.createEmptyBorder;
import static ui.MainGUI.*;

public class CreateCards implements FocusListener {

    public final static Font CALIBRI_ITALIC = new Font("Calibri", Font.ITALIC, 40);
    public final static Font CALIBRI_BOLD = new Font("Calibri", Font.BOLD, 30);
    protected JPanel createDeckPanel;
    private JPanel scrollArea;
    private JPanel header;
    private JTextArea title;
    private String userTitle;
    private JButton addCardButton;
    private JButton menuButton;
    private JScrollPane scrollPane;

    public CreateCards() {
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

        title = new RoundJTextArea(100);
        title.addFocusListener(this);
        title.setText("Type your title...");
        title.setColumns(20);
        title.setFont(CALIBRI_ITALIC.deriveFont(attributes));

        title.setForeground(BRIGHT_PINK);
        title.setBackground(new Color(255, 245, 245));
        title.setBorder(createEmptyBorder());
        title.setBorder(BorderFactory.createCompoundBorder(
                title.getBorder(),
                createEmptyBorder(15, 15, 15, 15)));

        title.setLineWrap(true);
        title.setWrapStyleWord(true);
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
    }

    // effects: a pop up message asking if user would like to save their cards
    // before exiting to the menu
    void warnUser() {
    }

    @Override
    // effects: none, irrelevant! (for now...)
    public void focusGained(FocusEvent e) {
    }

    @Override
    // saves the title entered
    public void focusLost(FocusEvent e) {
        JTextArea titleTextArea = (JTextArea) e.getSource();
        userTitle = titleTextArea.getText();
        System.out.println(userTitle);
    }

    // getters
    public JButton getMenuButton() {
        return menuButton;
    }

    public JButton getAddCardButton() {
        return addCardButton;
    }


    // effects: creates the beveled text box for the title of the deck
    // credits: https://stackoverflow.com/questions/8515601/java-swing-rounded-border-for-jtextfield
    private class RoundJTextArea extends JTextArea {
        private Shape shape;

        public RoundJTextArea(int size) {
            super(String.valueOf(size));
            setOpaque(false); // As suggested by @AVD in comment.
        }

        protected void paintComponent(Graphics g) {
            g.setColor(new Color(255, 245, 245));
            g.fillRoundRect(0, 0, getWidth() - 5, getHeight() - 5, 30, 30);
            super.paintComponent(g);
        }

        public boolean contains(int x, int y) {
            if (shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new RoundRectangle2D.Float(0, 0, getWidth() - 5, getHeight() - 5, 30, 30);
            }
            return shape.contains(x, y);
        }
    }
}
