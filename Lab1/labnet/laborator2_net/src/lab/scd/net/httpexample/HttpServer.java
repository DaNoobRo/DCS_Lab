/*
 * HttpServer.java
 */
package lab.scd.net.httpexample;

/**
 * Class created by @author Mihai HULEA at Feb 23, 2005.
 * 
 * This class is part of the laborator2_sockettest project.
 * 
 */
import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

public class HttpServer extends Thread
{
	//portul standard
	private final static int PORT = 80;
	
	private final String iniContext="c:/temp/ServerHTTP/webdocs";
	private boolean alive;
	
	private ServerSocket ss;
	
	//constructor
	HttpServer()throws Exception{
		System.out.println("Start server http.");
		ss = new ServerSocket(PORT);
		alive=true;
		start();
	}
	
	public void run(){
		while(alive){
			
			//asteapta conexiuni
			try{
			System.out.println("Server asteapta...");
			new ProcesRequest(ss.accept(),iniContext);
				
			}catch(IOException e){System.err.println("EROARE CONECTARE:"+e.getMessage());}
			//..reia bucla de asteptare dupa ce am creat un fir pentru client
		}
		System.out.println("STOP SERVER");
	}
	public static void main(String[] args)throws Exception
	{
		try{
		new HttpServer();
		}catch(Exception e){e.printStackTrace();}
	}
	
}



class ProcesRequest extends Thread
{
	private PrintWriter outStr;
	private BufferedReader inStr;
	private Socket s;
	private DataOutputStream dout;
	private String iniContext;

	ProcesRequest(Socket s, String iContext){
		try{
			outStr = new PrintWriter(new OutputStreamWriter(s.getOutputStream()),true);
			inStr = new BufferedReader(new InputStreamReader(s.getInputStream()));
			dout = new DataOutputStream(s.getOutputStream());
			iniContext = iContext;
			this.s = s;
			start();
		}catch(IOException e)
		{System.err.println("EROARE CONECTARE: "+e.getMessage());}
	}
	
	public void run(){
		try{
			String fileName=null;
			String request = inStr.readLine();
			System.out.print(request);
			if(request.lastIndexOf("GET")==0) fileName = interpretGET(request);
			else throw new Exception("BAU");
			byte[] data = readFile(fileName);
			dout.write(data);
			dout.flush();
					
		}
		catch(IOException e){outStr.println("<HTML><BODY><P>403 Forbidden<P></BODY></HTML>");}
		catch(Exception e2){outStr.println("<HTML><BODY><P>"+e2.getMessage()+"<P></BODY></HTML>");}
		finally{
			try{s.close();}catch(Exception e){}
		}
	}
	
	private String interpretGET(String rqst) throws Exception{
		StringTokenizer strT = new StringTokenizer(rqst);
		String tmp="";
		String fileN=iniContext;
		tmp=strT.nextToken();
		if(!tmp.equals("GET")) throw new Exception("Comanda GET invalida .");
		
		tmp=strT.nextToken();
		if((tmp.equals("/")) || (tmp.endsWith("/"))) {
			fileN = fileN+tmp+"index.htm";
			System.err.println("CERERE:"+fileN);
			return fileN;
		}
		
		fileN = fileN+ tmp;
		System.err.println("CERERE:"+fileN);
		return fileN;
	}
	
	private byte[] readFile(String fileN) throws Exception{
		fileN.replace('/','\\');
		File f = new File(fileN);
		if(!f.canRead()) throw new Exception("Fisierul "+fileN+" nu poate fi citit");
		FileInputStream fstr = new FileInputStream(f);
		byte[] data = new byte[fstr.available()];
		fstr.read(data);
		return data;
	}
}

