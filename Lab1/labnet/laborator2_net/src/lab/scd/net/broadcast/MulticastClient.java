/*
 * MulticastClient.java
 */
package lab.scd.net.broadcast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Class created by @author Mihai HULEA at Feb 26, 2005.
 * 
 * This class is part of the laborator2_net project.
 * 
 * 1. Modificati aplicatia client astfel incat adresa IP a serverului sa fie citita
 * ca si argument la lansarea in executie a programului.
 * 
 */
public class MulticastClient extends Thread{
    
    boolean alive=true;
    int port;
    
    
    
    /**
     * @param port
     */
    public MulticastClient(int port) {
        this.port = port;
    }
    
    public void run(){
        try{
            
        MulticastSocket socket = new MulticastSocket(port);
        //pregateste aplicatia client pentru a putea receptiona mesaje multicast
        InetAddress group = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(group);

        DatagramPacket packet;
        while(alive){
        	byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            //asteapta receptionarea de pachete
            socket.receive(packet);
            
            //extrage continultul mesajlui din pachet
            String received = new String(packet.getData());
            
            //afiseaza mesajul
            System.out.println("rcv: " + received);
        }
        socket.leaveGroup(group);
        socket.close();
        }catch(Exception e){
            System.err.println("Error on client : "+e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String msg, String serverIP) throws Exception{
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = new byte[256];
        InetAddress address = InetAddress.getByName(serverIP);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, 
                                                   address, port);
        
        packet.setData(msg.getBytes());
        
        socket.send(packet);
    }
    
  
    
    public static void main(String[] args) throws Exception{
        
     
       
        //start listening for brodcasted messages
        MulticastClient mc = new MulticastClient(4446);
        mc.start();
        
        
        //intr-o bucla while citeste de la tastatura linii de text
        String msg="";
        while(!msg.equals("exit")){
            
	        BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
	        System.out.print("snd:");
	        //citeste o linie de la tastatura
	        msg = sb.readLine();
	        try{
	         //trimte mesajul catre serverul multicast   
	         mc.sendMessage(msg,"127.0.0.1");
	        }catch(Exception e){
	            System.err.println("Error sending message:"+e.getMessage());
	        }
	    }
        System.exit(0);
      }
}
