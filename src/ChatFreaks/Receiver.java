package ChatFreaks;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Receiver extends Thread{

    private DatagramSocket socket = null;
    private JTextField inputField;

    public Receiver(String name, JTextField inputField) throws IOException {
        super(name);
        socket = new DatagramSocket(4445);
        this.inputField = inputField;
    }

    public void run() {
        while(true) {
            try {
                byte[] buf = new byte[256];

                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                socket.receive(packet);
                String dString = null;
                dString = inputField.getText();
                buf = dString.getBytes();

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}