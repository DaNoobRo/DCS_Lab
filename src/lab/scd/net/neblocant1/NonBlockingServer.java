/*
 * Created on Jan 13, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package lab.scd.net.neblocant1;

/**
 * @author mihai
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.channels.spi.*;
import java.nio.charset.*;
import java.net.*;
import java.util.*;


public class NonBlockingServer {

    int port = 8000;
    Selector selector = null;
    ServerSocketChannel selectableChannel = null;
    int keysAdded = 0;

    static String QUIT_SERVER = "quit";
    static String SHUTDOWN = "shutdown";

    public NonBlockingServer() {
    }

    public NonBlockingServer( int port ) {
        this.port = port;
    }

    public void initialize() throws IOException {
    	//A Selector object can be created using itself as a factory or using a SelectorProvider factory
    	this.selector = SelectorProvider.provider().openSelector();
        
        this.selectableChannel = ServerSocketChannel.open();
        this.selectableChannel.configureBlocking(false);
        InetAddress lh = InetAddress.getLocalHost();
        InetSocketAddress isa = new InetSocketAddress(lh, this.port );
        this.selectableChannel.socket().bind(isa);
    }

    public void finalize() throws IOException {
        this.selectableChannel.close();
        this.selector.close();
    }

    public void acceptConnections() throws IOException, InterruptedException {

      SelectionKey acceptKey =
            this.selectableChannel.register( this.selector,
                SelectionKey.OP_ACCEPT );

      System.err.println( "Acceptor loop..." );
        while (( this.keysAdded = acceptKey.selector().select()) > 0 ) {

        	System.err.println( "Selector returned "
                + this.keysAdded + " ready for IO operations" );

            Set readyKeys = this.selector.selectedKeys();
            Iterator i = readyKeys.iterator();
 
            while (i.hasNext()) {
                SelectionKey key = (SelectionKey)i.next();
                i.remove();

                if ( key.isAcceptable() ) {
                    ServerSocketChannel nextReady =
                        (ServerSocketChannel)key.channel();

                    System.err.println( "Processing selection key read="
                        + key.isReadable() + " write=" + key.isWritable() +
                        " accept=" + key.isAcceptable() );

                    SocketChannel channel = nextReady.accept();
                    channel.configureBlocking( false );
                    SelectionKey readKey =
                        channel.register( this.selector,
                            SelectionKey.OP_READ|SelectionKey.OP_WRITE  );
                    readKey.attach( new ChannelCallback( channel ) );
                }

                else if ( key.isReadable() ) {
                    SelectableChannel nextReady =
                        (SelectableChannel) key.channel();
                    System.err.println( "Processing selection key read="
                        + key.isReadable() + " write=" + key.isWritable() +
                        " accept=" + key.isAcceptable() );
                    this.readMessage( (ChannelCallback)  key.attachment() );
                }

                else if ( key.isWritable() ) {
                    ChannelCallback callback = (ChannelCallback) key.attachment();
                    String message = "What is your name? ";
                    ByteBuffer buf = ByteBuffer.wrap( message.getBytes() );
                    int nbytes = callback.getChannel().write( buf );
                }
            }
        }

        System.err.println( "End acceptor loop..." );

    }

 

    public void writeMessage( SocketChannel channel, String message )throws IOException {
        ByteBuffer buf = ByteBuffer.wrap( message.getBytes()  );
        int nbytes = channel.write( buf );
        System.err.println( "Wrote " + nbytes + " to channel." );
    }

 

    static final int BUFSIZE = 8;

    public String decode( ByteBuffer byteBuffer )
    throws CharacterCodingException {
        Charset charset = Charset.forName( "us-ascii" );
        CharsetDecoder decoder = charset.newDecoder();
        CharBuffer charBuffer = decoder.decode( byteBuffer );
        String result = charBuffer.toString();
        return result;
    }

    public void readMessage( ChannelCallback callback ) throws IOException, InterruptedException {
        ByteBuffer byteBuffer = ByteBuffer.allocate( BUFSIZE );
        int nbytes = callback.getChannel().read( byteBuffer );
        byteBuffer.flip();
        String result = this.decode( byteBuffer );
        System.err.println( result );
        if ( result.indexOf( "quit" ) >= 0 ) callback.getChannel().close();
        else if ( result.indexOf( "shutdown" ) >= 0 ) {
            callback.getChannel().close();
            throw new InterruptedException();
        }
        else {
            callback.append( result.toString() );
            //If we are done with the line then we execute the callback.
            if ( result.indexOf( "\n" ) >= 0 )
                callback.execute();
        }
    }

    public class ChannelCallback {
        private SocketChannel channel;
        private StringBuffer buffer;

        public ChannelCallback( SocketChannel channel ) {
            this.channel = channel;
            this.buffer = new StringBuffer();
        }

        public void execute() throws IOException {
        	System.err.println( this.buffer.toString() );
            writeMessage( this.channel, this.buffer.toString() );
            buffer = new StringBuffer();
        }

        public SocketChannel getChannel() {
            return this.channel;
        }

        public void append( String values ) {
            buffer.append( values );
        }

    }

 

    public static void main( String[] args ) {
       
        NonBlockingServer nbServer = new NonBlockingServer();

        try {
            nbServer.initialize();
        } catch ( IOException e ) {
            e.printStackTrace();
            System.exit( -1 );
        }

        try {
            nbServer.acceptConnections();
        }

        catch ( IOException e ) {
            e.printStackTrace();
            System.err.println( e );
        }

        catch ( InterruptedException e ) {
        	System.err.println( "Exiting normally..." );
        }

    }

}
