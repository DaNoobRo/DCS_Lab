/*
 * Created on Jan 13, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package lab.scd.net.neblocant1;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.*;

public class NonBlockingServer3 {
   private static int port = 8000;
   public static void main(String args[]) 
     throws Exception {
     Selector selector = Selector.open();

     ServerSocketChannel channel = 
       ServerSocketChannel.open();
     channel.configureBlocking(false);
     InetSocketAddress isa = new InetSocketAddress(port);
     channel.socket().bind(isa);

     // Register interest in when connection
     channel.register(selector, SelectionKey.OP_ACCEPT);

     // Wait for something of interest to happen
     while (selector.select() > 0) {
     	System.err.println("new event happened...");
       // Get set of ready objects
       Set readyKeys = selector.selectedKeys();
       Iterator readyItor = readyKeys.iterator();

       // Walk through set
       while (readyItor.hasNext()) {

         // Get key from set
         SelectionKey key = 
           (SelectionKey)readyItor.next();

         // Remove current entry
         readyItor.remove();

         if (key.isAcceptable()) {
           // Get channel
           ServerSocketChannel keyChannel =
             (ServerSocketChannel)key.channel();

           // Get server socket
           ServerSocket serverSocket = keyChannel.socket();

           // Accept request
           Socket socket = serverSocket.accept();

           // Return canned message
           PrintWriter out = new PrintWriter
             (socket.getOutputStream(), true);
           out.println("Hello, NIO");
           out.close();
         }
         if (key.isReadable()) {

		      SocketChannel client = (SocketChannel) key.channel();
		      
		      // Read byte coming from the client
		      int BUFFER_SIZE = 32;
		      ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		      try {
		        client.read(buffer);
		        
		      }
		      catch (Exception e) {
		        // client is no longer active
		      	client.close();
		      	
		        e.printStackTrace();
		        continue;
		      }

		      // Show bytes on the console
		      buffer.flip();
		      Charset charset=Charset.forName("ISO-8859-1");
		      CharsetDecoder decoder = charset.newDecoder();
		      CharBuffer charBuffer = decoder.decode(buffer);
		      System.out.println(charBuffer.toString());
		      continue;
		    }
         else {
           System.err.println("Ooops");
         }

       }
     }
     // Never ends
   }
}