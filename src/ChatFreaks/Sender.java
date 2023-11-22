package ChatFreaks;

import javax.swing.*;
import java.io.IOException;
import java.net.*;

class Sender extends Thread{

    private JTextField inputField;
    JTextArea outputArea;

    public Sender(JTextArea outputArea, JTextField inputField) {
        this.outputArea=outputArea;
        this.inputField=inputField;
    }

    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] buf = new byte[256];
            InetAddress adress = InetAddress.getByName("10.129.1.98");

            String send = null;
            send = inputField.getText();
            inputField.setText("");
            buf = send.getBytes();
            outputArea.append("\n (Jumeri)>"+send);

            DatagramPacket packet = new DatagramPacket(buf, buf.length, adress, 4446);
            socket.send(packet);
            socket.receive(packet);
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
