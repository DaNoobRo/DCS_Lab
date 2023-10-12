/*
 * MulticastServer.java
 */
package lab.scd.net.broadcast;


import java.net.*;

/**
 * Class created by @author Mihai HULEA at Feb 26, 2005.
 * 
 * This class is part of the laborator2_net project.
 * 
 */
public class MulticastServer extends Thread {

    DatagramSocket socket;
    boolean alive=true;
    
    public MulticastServer() throws SocketException{
        socket = new DatagramSocket(1977);
    }
    
    public void run(){
        
        try{
            System.out.println("Multicast server started.");
	        while(alive){
	            byte[] buf = new byte[256];
	
	            // receive request
	            DatagramPacket rcvdPacket = new DatagramPacket(buf, buf.length);
	            
	            //serverul asteapta primire de pachete
	            socket.receive(rcvdPacket);
	            /*
	            String received = new String(packet.getData());
	            System.out.println("server received: " + received);
	            */
	            
	            //adresa multicast catre care for fi trimise mesajele
	            InetAddress group = InetAddress.getByName("230.0.0.1");
	            
	            //construieste pachetul
	            DatagramPacket packet = new DatagramPacket(buf, buf.length, 
                        group, 4446);
	            
	            packet.setData(rcvdPacket.getData());
                
	            //trimte datele catre adresa multicast
	            socket.send(packet);
                
	        }
        
        }catch(Exception e){
            System.err.println("Server error: "+e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)throws Exception {
        MulticastServer ms = new MulticastServer();
        ms.start();
        /*
        MulticastClient mc = new MulticastClient();
        mc.start();
        
        String msg="";
        while(!msg.equals("exit")){
	        BufferedReader sb = new BufferedReader(new InputStreamReader(System.in));
	        System.out.print("snd:");
	        msg = sb.readLine();
	        try{
	        mc.sendMessage(msg,"127.0.0.1");
	        }catch(Exception e){
	            System.err.println("Error sending message:"+e.getMessage());
	        }
	   }//.while
        */
        //System.exit(0);
        
    }
}
