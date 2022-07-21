package ui;

import java.awt.*;

class Background extends Panel {

    Image img;
    String source;

    public Background(String source) {
        this.source = source;
        img = Toolkit.getDefaultToolkit().createImage(source);
    }

    public void paint(Graphics g){
        g.drawImage(img, 0, 0, null);
    }
}

