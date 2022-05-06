package ui;

import javax.swing.*;
import java.awt.*;

// sets up background
public abstract class Paint {
    public Paint() {
    }

    // effects: determines image to use/place
    public static class BackgroundImageTitle extends JComponent {
        private Image image;

        public BackgroundImageTitle(Image image) {
            this.image = image;
        }

        // paint image to background
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }
}

