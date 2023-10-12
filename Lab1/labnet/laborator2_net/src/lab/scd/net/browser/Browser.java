/*
 * Browser.java
 */
package lab.scd.net.browser;

/**
 * Class created by @author Mihai HULEA at Feb 25, 2005.
 * 
 * This class is part of the laborator2_net project.
 * 
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;


 class Browser extends JPanel {
  Browser() {
    setLayout (new BorderLayout (5, 5));
    final JEditorPane jt = new JEditorPane();
    final JTextField input = 
      new JTextField("http://127.0.0.1:8080");
    // read-only
    jt.setEditable(false);
    
    // follow links
    
    jt.addHyperlinkListener(new HyperlinkListener () {
      public void hyperlinkUpdate(
          final HyperlinkEvent e) {
        if (e.getEventType() == 
            HyperlinkEvent.EventType.ACTIVATED) {
          SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              // Save original
              Document doc = jt.getDocument();
              try {
                URL url = e.getURL();
                jt.setPage(url);
                input.setText (url.toString());
              } catch (IOException io) {
                JOptionPane.showMessageDialog (
                  Browser.this, "Can't follow link", 
                  "Invalid Input", 
                   JOptionPane.ERROR_MESSAGE);
                jt.setDocument (doc);
              }
            }
          });
        }
      }
    });
    
    JScrollPane pane = new JScrollPane();
    pane.setBorder (
      BorderFactory.createLoweredBevelBorder());
    pane.getViewport().add(jt);
    add(pane, BorderLayout.CENTER);

    input.addActionListener (new ActionListener() {
      public void actionPerformed (ActionEvent e) {
        try {
          jt.setPage (input.getText());
        } catch (IOException ex) {
          JOptionPane.showMessageDialog (
            Browser.this, "Invalid URL", 
            "Invalid Input", 
            JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    add (input, BorderLayout.SOUTH);
  }
}




