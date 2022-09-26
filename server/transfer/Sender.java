package server.transfer;

import common.transfer.Request;
import common.transfer.Response;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

public class Sender implements Runnable {
    DatagramSocket datagramSocket;
    ResponseQueue responseQueue;

    public Sender(ResponseQueue responseQueue) {
        this.responseQueue = responseQueue;
        int port = 4000;
        while (true) {
            try {
                datagramSocket = new DatagramSocket(port);
                datagramSocket.setSoTimeout(90000);
                break;
            } catch (SocketException e) {
                ++port;
            }
        }
    }

    public void send(Response response) {
        try {
            File bufferFile = new File(getBufferFileName());
            bufferFile.createNewFile();
            OutputStream bufferOutput = new FileOutputStream(bufferFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferOutput);
            objectOutputStream.writeObject(response);
            objectOutputStream.close();

            InputStream bufferInput = new FileInputStream(bufferFile);
            byte[] data = new byte[1024];
            while (bufferInput.read(data) != -1) {
                DatagramPacket datagramPacket = new DatagramPacket(data, data.length, response.getClientAddress());
                datagramSocket.send(datagramPacket);
            }
            datagramSocket.send(new DatagramPacket(new byte[1], 1, response.getClientAddress()));
            bufferInput.close();
            bufferFile.delete();
        } catch (IOException ignore) {}
    }

    @Override
    public void run() {
        Response response;
        while (true) {
            response = responseQueue.pollFirst();
            if (response != null) {
                send(response);
            }
        }
    }

    private String getBufferFileName() {
        long time = new Date().getTime();
        long rand = Math.round(Math.random()*100000);
        return time + "" + rand + ".bin";
    }
}
