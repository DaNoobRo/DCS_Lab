/*
 * SimpleBrowser.java
 */
package lab.scd.net.browser_simple;

/**
 * Class created by @author Mihai HULEA at Feb 25, 2005.
 * 
 * This class is part of the laborator2_net project.
 * 
 */

import javax.swing.*;

import java.io.*;
import java.util.Properties;

public class SimpleBrowser {
    
public static void main(String[] args) {
    
    Properties props = System.getProperties();

    //eliminati comentariile in cazul in care folosti un proxy 
    /*
    props.put("http.proxyHost","193.226.5.55");
    props.put("http.proxyPort","3128");
*/
    // get the first URL
	String initialPage = "http://google.com";
	//if (args.length > 0) initialPage = args[0];
	// set up the editor pane
	JEditorPane jep = new JEditorPane( );
	jep.setEditable(false);
	jep.addHyperlinkListener(new LinkFollower(jep));

	try {
	    jep.setPage(initialPage);
	}
	catch (IOException e) {
		System.err.println("Usage: java SimpleWebBrowser url");
		System.err.println(e);
		System.exit(-1);
	}
	
	// set up the window
	JScrollPane scrollPane = new JScrollPane(jep);
	JFrame f = new JFrame("Simple Web Browser");
	f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	f.setContentPane(scrollPane);
	f.setSize(512, 342);
	f.setVisible(true);

	}
}