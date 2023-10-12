/*
 * ServerMultifir.java
 */

/**
 * Class created by @author Mihai HULEA at Feb 23, 2005.
 * 
 * This class is part of the laborator2_sockettest project.
 * 
 */
package lab.scd.net.socket;
import java.io.*;
import java.net.*;

public class ServerMultifir 
{
	public static final int PORT = 1900;
	void startServer()
	{
		ServerSocket ss=null;
		try
		{
		
		ss = new ServerSocket(PORT);
		while (true)
		{
		    //astept conexiuni
			Socket socket = ss.accept();
			//un client s-a conectat...starteaza firul de tratare a clientului
			new TratareClient(socket);
		}
		
		}catch(IOException ex)
		{
			System.err.println("Eroare :"+ex.getMessage());
		}
		finally
		{
			try{ss.close();}catch(IOException ex2){}
		}
	}

	public static void main(String args[])
	{
		ServerMultifir smf = new ServerMultifir();
		smf.startServer();
	}


}

/**
 * Class created by @author Mihai HULEA at Mar 13, 2005.
 * 
 * This class is part of the laborator2_net project.
 *
 * Se foloseste cate un fir de executie pentru tratarea fiecarui client.
 */
class TratareClient extends Thread
{
	  private Socket socket;
	  private BufferedReader in;
      private PrintWriter out;
      
      /**
       * Initializeaza firul de executie
       * @param socket
       * @throws IOException
       */
	  TratareClient(Socket socket)throws IOException 
	  {
	  	this.socket = socket;
	  
	  	//init flux de intrare prin care se vor citi datele de la client
	  	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
	  	//inti flux de iesire prin care datele vor fi trimise catre client
	  	out = new PrintWriter(
		        new BufferedWriter(
			        new OutputStreamWriter( socket.getOutputStream())));
	  
	  	//sunt folosite doua fluxuri orientate pa caracter
	  	
	  }
	  
		  public void run()
		  {
		  try {
		      
		  while (true) 
		  {  
		      //asteapta receptionarea unui mesaj de la client
	           String str = in.readLine();
	           
	           //daca mesajul este egal cu "END" opreste firul ce trateaza clientul
	           if (str.equals("END")) break;
	           //afiseaza mesajul pe ecran
	           System.out.println("Echoing: " + str);
	           
	           //trimte catre client mesajul receptionat, adaugand in fata sirul "echo"
	           out.println("echo "+str);
	           out.flush();
	           
	          }//.while
	      	  System.out.println("closing...");
	      	  
	      	  } 
		  catch(IOException e) {System.err.println("IO Exception");} 
		  finally {
	          try {
	           socket.close();
	      	}catch(IOException e) {System.err.println("Socket not closed");}
	      }
		}//.run
}
