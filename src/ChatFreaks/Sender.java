package ChatFreaks;

import javax.swing.*;
import java.io.IOException;
import java.net.*;

class Sender extends Thread{

    JTextArea outputArea;
    public Sender(JTextArea outputArea) {
        this.outputArea=outputArea;
    }

    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] buf = new byte[256];
            InetAddress address = InetAddress.getByName("10.129.1.98");
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);

            packet= new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            String received = new String(packet.getData());
            outputArea.append("\n>"+received);

            socket.close();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
