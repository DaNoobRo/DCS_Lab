/*
 * MainApp.java
 */
package lab.scd.net.browser;

import javax.swing.JFrame;

/**
 * Class created by @author Mihai HULEA at Feb 25, 2005.
 * 
 * This class is part of the laborator2_net project.
 * 
 */
public class MainApp extends JFrame{
    
    public MainApp(){
    		Browser b = new Browser();
    		getContentPane().add(b);
    		pack();
    		setVisible(true);
    		}

    public static void main(String args[])
    	{
        //eliminati comentariile in cazul in care folosti un proxy 
        /*
        props.put("http.proxyHost","193.226.5.55");
        props.put("http.proxyPort","3128");
        */
        MainApp s = new MainApp();
    	}
  
}