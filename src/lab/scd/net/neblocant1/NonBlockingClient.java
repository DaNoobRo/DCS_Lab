/*
 * Created on Jan 13, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package lab.scd.net.neblocant1;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.channels.spi.*;
import java.nio.charset.*;
import java.net.*;
import java.util.*;
/**
 * @author mihai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NonBlockingClient {
	
	
	public static void main(String[] args) throws IOException {

		
//		 Create client SocketChannel
		SocketChannel client = SocketChannel.open();

//		 nonblocking I/O
		client.configureBlocking(false);

//		 Connection to host port 8000
		client.connect(new java.net.InetSocketAddress("localhost",8000));

//		 Create selector
		Selector selector = Selector.open();

//		 Record to selector (OP_CONNECT type)
		SelectionKey clientKey = client.register(selector, SelectionKey.OP_CONNECT);

//		 Waiting for the connection
		while (selector.select(500)> 0) {
			
		  System.err.println("Start communication...");
		  
		  // Get keys
		  Set keys = selector.selectedKeys();
		  Iterator i = keys.iterator();

		  // For each key...
		  while (i.hasNext()) {
		    SelectionKey key = (SelectionKey)i.next();

		    // Remove the current key
		    i.remove();

		    // Get the socket channel held by the key
		    SocketChannel channel = (SocketChannel)key.channel();

		    // Attempt a connection
		    if (key.isConnectable()) {

		      // Connection OK
		      System.out.println("Server Found");

		      // Close pendent connections
		      if (channel.isConnectionPending())
		        channel.finishConnect();

		      // Write continuously on the buffer
		      ByteBuffer buffer = null;
		      int x=0;
		      for (;x<7;) {
		      	x++;
		        buffer = 
		          ByteBuffer.wrap(
		            new String(" Client " + x + " "+x).getBytes());
		        channel.write(buffer);
		        buffer.clear();
		        try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		      }
		      channel.finishConnect();
		      client.close();
		    }
		  }
		}
		System.err.println("Client terminated.");
	}
}
