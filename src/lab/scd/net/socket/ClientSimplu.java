/*
 * ClientSimplu.java
 */

/**
 * Class created by @author Mihai HULEA at Feb 23, 2005.
 * 
 * This class is part of the laborator2_serverclientmonofir project.
 * 
 * 1. Modificati aplicatia client astfel incat aceasta sa trimita catre server mesaje
 * citite de la tastatura. 
 */
package lab.scd.net.socket;
import java.net.*;
import java.io.*;

public class ClientSimplu {

  public static void main(String[] args)throws Exception{
    Socket socket=null;
    try {
      //creare obiect address care identifica adresa serverului
      InetAddress server_address =InetAddress.getByName("localhost");
      //se putea utiliza varianta alternativa: InetAddress.getByName("127.0.0.1") getByName("10.129.1.98");
      
      socket = new Socket(server_address,1900);

      //construieste fluxul de intrare prin care sunt receptionate datele de la server
      BufferedReader in =
        new BufferedReader(
          new InputStreamReader(
            socket.getInputStream()));
      
      //construieste fluxul de iesire prin care datele sunt trimise catre server
      // Output is automatically flushed
      // by PrintWriter:
      PrintWriter out =
        new PrintWriter(
          new BufferedWriter(
            new OutputStreamWriter(
              socket.getOutputStream())),true);

      String x="24";
      String y="31";

      out.println(x);
      out.println("ENDx"); //trimite mesaj care determina serverul sa inchida conexiunea

      out.println(y);
      out.println("ENDy"); //trimite mesaj care determina serverul sa inchida conexiunea
      String str = in.readLine(); //trimite mesaj
      System.out.println(str);
    }
    catch (Exception ex) {ex.printStackTrace();}
    finally{
      socket.close();
    }
  }
}

