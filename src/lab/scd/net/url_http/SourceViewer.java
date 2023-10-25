/*
 * SourceViewer.java
 */
package lab.scd.net.url_http;

/**
 * Class created by @author Mihai HULEA at Feb 25, 2005.
 * 
 * This class is part of the laborator2_net project.
 * 
 */
import java.net.*;
import java.io.*;

public class SourceViewer {
    
	public static void main (String[] args) {
	    
		String url = "http://www.utcluj.ro";
		    
			try {

			    //Open the URL for reading
				URL u = new URL(url);
				InputStream in = u.openStream( );
				// buffer the input to increase performance
				in = new BufferedInputStream(in);
				// chain the InputStream to a Reader
				Reader r = new InputStreamReader(in);
				int c;
				while ((c = r.read( )) != -1) {
				    System.out.print((char) c);
				}
			}
			catch (MalformedURLException e) {
			System.err.println(args[0] + " is not a parseable URL");
			}
			catch (IOException e) {
			System.err.println(e);
			}
					
	} // 
} // end SourceViewer