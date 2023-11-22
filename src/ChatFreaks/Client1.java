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

            JFrame frame = new JFrame("Chat App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(640, 640);

            JTextArea heading = new JTextArea();
            heading.setBounds(10, 10, 600, 50);
            heading.setText("Chat Site");
            heading.setEditable(false);
            frame.add(heading);

            JTextField inputField = new JTextField(" <Write Here> ");
            inputField.setBounds(10, 410, 500, 30);
            frame.add(inputField);

            JTextArea outputArea = new JTextArea();
            outputArea.setBounds(10, 100, 600, 300);
            outputArea.setEditable(false);
            frame.add(outputArea);

            JButton button = new JButton("Send");
            button.setBounds(530, 410, 80, 30);
            button.setBackground(Color.GREEN);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Sender(outputArea, inputField).start();
                }
            });
            frame.add(button);

            frame.setLayout(null);
            frame.setVisible(true);

            new Receiver("Serve", outputArea).start();
        }
    }





