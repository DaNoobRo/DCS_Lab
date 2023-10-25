package ChatFreaks;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Client1 {
    public static void main(String args[]) throws IOException {
        JFrame frame = new JFrame("Messaging App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 640);
        frame.getContentPane().setBackground(new Color(64, 179, 196));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.setBackground(new Color(64,179 , 255));
        inputPanel.setBorder(new RoundedBorder(10));
        frame.add(inputPanel);

        JTextField inputField = new JTextField("Write Here");
        inputField.setPreferredSize(new Dimension(500, 30));
        inputField.setBorder(new RoundedBorder(10));
        inputPanel.add(inputField);

        JTextArea outputArea = new JTextArea();
        outputArea.setPreferredSize(new Dimension(600, 300));
        outputArea.setBorder(new RoundedBorder(10));
        outputArea.setEditable(false);
        frame.add(outputArea);

        JButton button = new JButton("Send");
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setBorder(new RoundedBorder(10));
        button.addActionListener(e -> new Sender(outputArea).start());
        frame.add(button);

        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        frame.setVisible(true);

        new Receiver("Serve", inputField).start();
    }
}

class RoundedBorder extends AbstractBorder {
    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        super.paintBorder(c, g, x, y, width, height);
        g.setColor(Color.GRAY);
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}



