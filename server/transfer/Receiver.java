package server.transfer;

import common.transfer.Request;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

public class Receiver implements Runnable {
    final int MAX_BUFFER_SIZE = 1024;
    final DatagramSocket datagramSocket;
    final DatagramPacket datagramPacket;
    final byte[] buffer = new byte[MAX_BUFFER_SIZE];
    final RequestQueue requestQueue;

    public Receiver(int port, RequestQueue requestQueue) throws SocketException {
        this.requestQueue = requestQueue;
        datagramSocket = new DatagramSocket(port);
        datagramSocket.setSoTimeout(100000);
        datagramPacket = new DatagramPacket(buffer, MAX_BUFFER_SIZE);
    }

    @Override
    public void run() {
        try {
            File bufferFile = new File(getBufferFileName());
            bufferFile.createNewFile();
            FileOutputStream bufferFileOutput = new FileOutputStream(bufferFile);

            int len = 0;
            while (len != 1) {
                datagramSocket.receive(datagramPacket);
                len = datagramPacket.getLength();
                if (len > 0) {
                    bufferFileOutput.write(buffer, 0, len);
                    bufferFileOutput.flush();
                }
            }

            bufferFileOutput.close();
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(bufferFile));
            Request request = (Request) objectInputStream.readObject();
            request.setClientAddress(datagramPacket.getSocketAddress());
            requestQueue.addLast(request);
            objectInputStream.close();
            bufferFile.delete();
        } catch (IOException | ClassNotFoundException ignore) {}
        finally {
            datagramSocket.close();
        }
    }

    private String getBufferFileName() {
        long time = new Date().getTime();
        long rand = Math.round(Math.random()*100000);
        return time + "" + rand + ".bin";
    }
}
