package ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

import static ui.MainGUI.*;

public class Theme {

    protected JPanel themeSettingPanel;
    private JPanel minimalistPanel;
    private JPanel cutesyPanel;
    private JPanel edgyPanel;

    public Theme() {
        setUpBasePanel();
        setUpMinimalistPanel();
        setUpCutesyPanel();
        setUpEdgyPanel();
        minimalistPanel.setVisible(false);
        cutesyPanel.setVisible(false);
        edgyPanel.setVisible(false);
    }

    private void setUpEdgyPanel() {
    }

    private void setUpCutesyPanel() {
        
    }

    private void setUpMinimalistPanel() {
        minimalistPanel.setBackground(Color.white);
        minimalistPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    private void setUpBasePanel() {
        themeSettingPanel = new JPanel();
        themeSettingPanel.setBackground(LIGHT_PINK);
        themeSettingPanel.setLayout(new BoxLayout(themeSettingPanel, BoxLayout.Y_AXIS));
        minimalistPanel = new JPanel(new MigLayout("align 50% 50%"));
        cutesyPanel = new JPanel(new MigLayout("align 50% 50%"));
        edgyPanel = new JPanel(new MigLayout("align 50% 50%"));
        themeSettingPanel.add(minimalistPanel);
        themeSettingPanel.add(cutesyPanel);
        themeSettingPanel.add(edgyPanel);
    }
}
