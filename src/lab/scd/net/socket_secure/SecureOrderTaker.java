/*
 * SecureOrderTaker.java
 */
package lab.scd.net.socket_secure;

/**
 * Class created by @author Mihai HULEA at Feb 25, 2005.
 * 
 * This class is part of the laborator2_net project.
 * 
 */
import java.net.*;
import java.io.*;

import java.security.*;
import javax.net.ssl.*;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

/*
    Starteaza un secure SSL server socket.
    
    Inainte de starearea aplicatie trebuie generata cheia utilizand utilitarul keytool  
   
	D:\JAVA>keytool -genkey -alias ourstore -keystore jnp2e19.keys
	Enter keystore password: studenti
	What is your first and last name?
	[Unknown]: Elliotte
	What is the name of your organizational unit?
	[Unknown]: Me, Myself, and I
	What is the name of your organization?
	[Unknown]: Cafe au Lait
	What is the name of your City or Locality?
	[Unknown]: Brooklyn
	What is the name of your State or Province?
	[Unknown]: New York
	What is the two-letter country code for this unit?
	[Unknown]: NY
	Is <CN=Elliotte, OU="Me, Myself, and I", O=Cafe au Lait, L=Brooklyn,
	ST=New York, C=NY> correct?
	[no]: y
	Enter key password for <ourstore>
	(RETURN if same as keystore password):
*/


public class SecureOrderTaker {
    public final static int DEFAULT_PORT = 7000;
    public final static String algorithm = "SSLv3";

    public static void main(String[] args) {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
        try {
        port = Integer.parseInt(args[0]);
        if (port <= 0 || port >= 65536) {
        System.out.println("Port must between 1 and 65535");
        return;
        }
        }
        catch (NumberFormatException e) {}
        }
        try {
        SSLContext context = SSLContext.getInstance("SSL");
//         The reference implementation only supports X.509 keys
        KeyManagerFactory kmf =
        KeyManagerFactory.getInstance("SunX509");
//         Sun's default kind of key store
        KeyStore ks = KeyStore.getInstance("JKS");
//         For security, every key store is encrypted with a
//         pass phrase that must be provided before we can load
//         it from disk. The pass phrase is stored as a char[] array
//         so it can be wiped from memory quickly rather than
//         waiting for a garbage collector. Of course using a string
//         literal here completely defeats that purpose.
        char[] password = "studenti".toCharArray( );
        ks.load(new FileInputStream(new File("d:\\myprogramms\\java\\labs\\jnp2e19.keys")), password);
        kmf.init(ks, password);
        //
        context.init(kmf.getKeyManagers( ), null, null);
        SSLServerSocketFactory factory
        = context.getServerSocketFactory( );
        SSLServerSocket server
        = (SSLServerSocket) factory.createServerSocket(port);
//         Now all the set up is complete and we can focus
//         on the actual communication.
        try {
        while (true) {
//         This socket will be secure,
//         but there's no indication of that in the code!
        System.out.println("Server started. Waiting...");
        Socket theConnection = server.accept( );
        System.out.println("Client connection accepted.");
        InputStream in = theConnection.getInputStream( );
        int c;
        while ((c = in.read( )) != -1) {
        System.out.write(c);
        }
        theConnection.close( );
        } // end while
        } // end try
        catch (IOException e) {
        System.err.println(e);
        } // end catch
        } // end try
        catch (IOException e) {
        e.printStackTrace( );
        } // end catch
        catch (KeyManagementException e) {
        e.printStackTrace( );
        } // end catch
        catch (KeyStoreException e) {
        e.printStackTrace( );
        } // end catch
        catch (NoSuchAlgorithmException e) {
        e.printStackTrace( );
        } // end catch
        catch (java.security.cert.CertificateException e) {
        e.printStackTrace( );
        } // end catch
        catch (UnrecoverableKeyException e) {
        e.printStackTrace( );
        } // end catch
        } // end main
}