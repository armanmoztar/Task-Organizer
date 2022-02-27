package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

// Creates a splash screen that is loaded before the TaskListGui class is run
public class SplashScreen {
    private JFrame frame;
    private JPanel panel;
    private JLabel image;
    private JProgressBar progressBar;

// Code referenced from: https://www.tutorialsfield.com/java-splash-screen-with-progress-bar/
// Code referenced from: https://www.tutorialspoint.com/swing/swing_imageicon.htm

    //EFFECTS: calls methods of SplashScreen class and sets frame
    public SplashScreen() {
        prepareGUI();
        addImage();
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.invalidate();
        frame.repaint();
        addProgressBar();
        runningBar();
    }

    //MODIFIES: this
    //EFFECTS: Creates panel and frame and sets dimensions
    private void prepareGUI() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(500, 250);

        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setSize(500, 300);
    }

    //REQUIRES: Image must be present in data folder and be in png or jpg format
    //MODIFIES: this
    //EFFECTS: Retrieves and adds image from data folder and sets dimensions
    public void addImage() {
        try {
            image = new JLabel(createImageIcon("./data/splash.png"));
        } catch (MalformedURLException me) {
            System.out.println("cannot find URL");
        }
        image.setSize(500, 290);
        panel.add(image);
    }

    //EFFECTS: Returns an ImageIcon, or null if path invalid
    private static ImageIcon createImageIcon(String path) throws MalformedURLException {
        java.net.URL imgURL = new File(path).toURL();
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    //MODIFIES: this
    //EFFECTS: Adds progress bar to frame
    public void addProgressBar() {
        progressBar = new JProgressBar();
        progressBar.setBounds(60, 280, 380, 30);//Setting Location and size
        progressBar.setStringPainted(true);//Setting String painted property
        progressBar.setValue(0);//setting progress bar current value
        progressBar.setForeground(new Color(47, 0, 89));
        frame.add(progressBar);//adding progress bar to frame
    }

    //REQUIRES: ProgressBar must be present on screen for bar to be visible
    //MODIFIES: this
    //EFFECTS: Sets and adds the status and speed of progress bar
    public void runningBar() {
        int i = 0;

        while (i <= 100) {
            try {
                Thread.sleep(35); //Pauses execution for 35 milliseconds
                progressBar.setValue(i);
                i++;
                if (i == 100) {
                    frame.dispose();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}