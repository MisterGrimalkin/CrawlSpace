package net.amarantha.crawlspace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by grimalkin on 21/06/15.
 */
public class UDPTest {

    byte[] ip = {(byte) 192, (byte) 168, 0, 20 };

    public void doTest() throws IOException {

        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress ipAddress = InetAddress.getByAddress(ip);
        byte[] sendData = new byte[1];
        sendData[0] = 1;
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 3000);
        clientSocket.send(sendPacket);
        clientSocket.close();

    }
}
