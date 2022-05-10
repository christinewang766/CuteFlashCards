package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

// effects: creates the beveled text box for the title of the deck
// credits: https://stackoverflow.com/questions/8515601/java-swing-rounded-border-for-jtextfield
public class RoundJTextArea extends JTextArea {
    private Shape shape;

    public RoundJTextArea(String text) {
        super(text);
        setOpaque(false);
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

