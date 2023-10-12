/*
 * LinkFolower.java
 */
package lab.scd.net.browser_simple;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * Class created by @author Mihai HULEA at Feb 25, 2005.
 * 
 * This class is part of the laborator2_net project.
 * 
 */
public class LinkFollower implements HyperlinkListener {
    
    private JEditorPane pane;
    public LinkFollower(JEditorPane pane) {
    this.pane = pane;
    }
    public void hyperlinkUpdate(HyperlinkEvent evt) {
    if (evt.getEventType( ) == HyperlinkEvent.EventType.ACTIVATED) {
    try {
        pane.setPage(evt.getURL( ));
    }
    catch (Exception e) {
    }
    }

    }

}