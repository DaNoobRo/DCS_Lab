/*
 * DisplayHTML.java
 */

/**
 * Class created by @author Mihai HULEA at Feb 25, 2005.
 * 
 * This class is part of the laborator2_net project.
 * 
 */

package lab.scd.net.displayhtml;
import javax.swing.*;
import java.io.*;

public class DisplayHTML {
    
	public static void main(String[] args) {
	    
		JEditorPane jep = new JEditorPane( );
		jep.setEditable(false);
		
		try {
		    jep.setPage("http://www.personal.ro");
		}
		catch (IOException e) {
			jep.setContentType("text/html");
			jep.setText("<html>Could not load http://www.personal.ro</html>");
		}
		
		JScrollPane scrollPane = new JScrollPane(jep);
		JFrame f = new JFrame("Test loading html page");
		
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setContentPane(scrollPane);
		f.setSize(512, 342);
		f.setVisible(true);
		
	}
}
