package ui;

import javax.swing.*;
import java.awt.*;

public class MainForm {
    public static void main(String args[]) {
        // Main Frame
        JFrame mainFrame = new JFrame("Travel Agency");

        JButton customerRegistryBtn = createButton("Login/Sign Up", 2, 2, 85, 25);
        JButton flightsListBtn = createButton("Show All Flights", 90, 2, 95, 25);
        JButton orderHistoryBtn = createButton("History Of Orders", 188, 2, 105, 25);

        mainFrame.add(customerRegistryBtn);
        mainFrame.add(flightsListBtn);
        mainFrame.add(orderHistoryBtn);

        configureMainFrame(mainFrame);
    }

    private static void configureMainFrame(JFrame frame) {
        frame.setSize(450, 300);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setBounds(x, y, width, height);
        button.setBackground(new Color(239,62,86));
        button.setBorder(BorderFactory.createLineBorder(Color.white));
        return button;
    }
}
