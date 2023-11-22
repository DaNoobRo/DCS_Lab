package ChatFreaks;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Receiver extends Thread{

    private DatagramSocket socket = null;

    JTextArea outputArea;
    public Receiver(String name,JTextArea outputArea) throws IOException {
        super(name);
        socket = new DatagramSocket(4445);
        this.outputArea = outputArea;
    }

    public void run() {
        while(true) {
            try {
                byte[] buf = new byte[256];

                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet= new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String received = new String(packet.getData());
                outputArea.append("\n (Carnati) >>"+received);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}