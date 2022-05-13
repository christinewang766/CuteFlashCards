package ui;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static javax.swing.BorderFactory.createEmptyBorder;
import static ui.CreateCards.CALIBRI_BOLD;
import static ui.HelperMethods.*;
import static ui.MainGUI.*;
import static ui.MainGUI.LIGHT_PINK;

public class FindFile implements FocusListener {

    protected JPanel findFilePanel;
    private JTextArea scrollTextPanel;
    private JScrollPane scrollPane;
    private JTextField textField;

    private JButton findFileButton;
    private JButton menuButton;
    private String source;


    public FindFile() {
        setUpFindPanel();
    }

    // effects: groups all the components together
    // includes: icon, scroll, buttons, text field
    private void setUpFindPanel() {

        findFilePanel = new JPanel(new MigLayout("align 50% 50%"));
        findFilePanel.setBackground(LIGHT_PINK);

        // credits: https://visualpharm.com/free-icons/live%20folder-595b40b65ba036ed117d2f2a
        Icon icon = new ImageIcon("src/images/folder.png");
        JLabel loadingGif = new JLabel(icon);
        loadingGif.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel scrollLabel = new JLabel("Save Files Identified",JLabel.LEFT);
        scrollLabel.setFont(CONSOLAS);
        scrollLabel.setForeground(BRIGHT_PINK);

        setUpScroll();

        findFilePanel.add(loadingGif, "wrap, center");
        findFilePanel.add(scrollLabel ,"wrap, center");
        findFilePanel.add(scrollPane, "wrap, center");
        setUpTextBox();
    }

    // effects: sets up the scroll that displays all pre-existing json files found
    private void setUpScroll() {
        // init the panel and scroll as well as functional/physical attributes
        scrollTextPanel = new JTextArea();
        scrollTextPanel.setOpaque(false);
        scrollTextPanel.setFont(CONSOLAS);
        scrollTextPanel.setBorder(new CompoundBorder(scrollTextPanel.getBorder(),
                new EmptyBorder(30,30,30,30)));

        scrollPane = new JScrollPane(scrollTextPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setBackground(new Color(255, 194, 199));

        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 700, HEIGHT - 700));
        scrollPane.setBorder(createEmptyBorder());
        getFileNamesFound();

        scrollPane.setViewport(backgroundViewPort("src/images/pinkPaintBackground.jpg"));
        scrollPane.setViewportView(scrollTextPanel);
    }

    // effects: searches through data folder and prints out the names
    // of each file ending in .json every 5 seconds
    private void getFileNamesFound() {
        // credits: https://stackoverflow.com/questions/12908412/print-hello-world-every-x-seconds
        Runnable run = () -> {
            File f = new File("data");
            FilenameFilter filter = (f1, name) -> name.endsWith(".json");
            ArrayList<String> noJson = new ArrayList<>();
            for(String name : f.list(filter)) {
                String noJsonName = name.substring(0, name.lastIndexOf('.'));
                noJson.add("\u2665 " + noJsonName);
            }
            String files = String.join("\n", noJson);
            scrollTextPanel.setText(files);
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(run, 0, 5, TimeUnit.SECONDS);
    }

    // effects: checks if there are any files in the data folder
    // that can be loaded
    protected Boolean hasLoadable() {
        if (scrollTextPanel.getText().length() == 0) {
            return false;
        } else {
            return true;
        }
    }

    // effects: creates the text box to select deck desired as well as
    // confirmation search button
    private void setUpTextBox() {
        JPanel holdTextComponents = new JPanel(new MigLayout());
        Font calItalic = new Font("Calibri", Font.ITALIC, 20);
        textField = new JTextField("Enter a file name...");
        textField.setColumns(25);
        textField.setFont(calItalic);
        textField.addFocusListener(this);

        JLabel nameLabel = new JLabel("File Name:");
        nameLabel.setFont(CALIBRI_BOLD);
        nameLabel.setForeground(BRIGHT_PINK);
        findFileButton = new JButton("Find");
        makeJOptionButtons(findFileButton);

        holdTextComponents.add(nameLabel, "wrap, gapy 15");
        holdTextComponents.add(textField);
        holdTextComponents.add(findFileButton);
        this.menuButton = createMenuButton();
        holdTextComponents.add(menuButton);
        findFilePanel.add(holdTextComponents);
    }

    // getters
    public JButton getFindFileButton() {
        return this.findFileButton;
    }

    public JButton getMenuButton() {
        return this.menuButton;
    }

    public String getSource() {
        this.source = "./data/" + textField.getText() + ".json";
        return this.source;
    }

    @Override
    public void focusGained(FocusEvent e) {
        JTextField tf = (JTextField) e.getSource();
        tf.setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
    }
}
