package client.transfer;

import common.transfer.ConnectionError;
import common.transfer.Request;
import common.transfer.Response;

import java.io.*;
import java.net.*;
import java.util.Date;

public class Transfer {
    SocketAddress mainServerAddress;
    SocketAddress temporaryAddress;
    DatagramSocket datagramSocket;

    public Transfer() {
        this(2548);
    }

    public Transfer(int mainServerPort) {
        try {
            mainServerAddress = new InetSocketAddress(InetAddress.getLocalHost(), mainServerPort);
        } catch (UnknownHostException e) {
            throw new ConnectionError("Локальный сервер не найден!");
        }
        int port = 1700;
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

    public Response receive() {
        try {
            byte[] buffer = new byte[1024];
            DatagramPacket datagramPacket = new DatagramPacket(buffer, 1024);
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
            Response response = (Response) objectInputStream.readObject();
            objectInputStream.close();
            return response;
        } catch (IOException | ClassNotFoundException ignore) {
            throw new ConnectionError("in client receiver");
        }
    }

    public void send(Request request) {
        setTemporaryAddress();
        try {
            File bufferFile = new File(getBufferFileName());
            bufferFile.createNewFile();
            OutputStream bufferOutput = new FileOutputStream(bufferFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferOutput);
            objectOutputStream.writeObject(request);
            objectOutputStream.close();

            InputStream bufferInput = new FileInputStream(bufferFile);
            byte[] data = new byte[1024];
            while (bufferInput.read(data) != -1) {
                DatagramPacket datagramPacket = new DatagramPacket(data, data.length, temporaryAddress);
                datagramSocket.send(datagramPacket);
            }
            datagramSocket.send(new DatagramPacket(new byte[1], 1, temporaryAddress));
            bufferInput.close();
            bufferFile.delete();
        } catch (IOException ignore) {}
    }

    private String getBufferFileName() {
        long time = new Date().getTime();
        long rand = Math.round(Math.random()*100000);
        return time + "" + rand + ".bin";
    }

    public void setTemporaryAddress() {
        try {
            datagramSocket.send(new DatagramPacket(new byte[1], 1, mainServerAddress));

            byte[] buffer = new byte[4];
            DatagramPacket packet = new DatagramPacket(buffer, 4);
            datagramSocket.receive(packet);
            //todo: check
            temporaryAddress = new InetSocketAddress(InetAddress.getLocalHost(), byteArrayToInt(buffer));
        } catch (IOException ignore) {}
    }

    private byte[] intToByteArray(int number) {
        byte[] data = new byte[4];
        for (int i = 0; i < 4; ++i) {
            int shift = i << 3; // i * 8
            data[3-i] = (byte)((number & (0xff << shift)) >>> shift);
        }
        return data;
    }

    private int byteArrayToInt(byte[] data) {
        int number = 0;
        for (int i = 0; i < 4; ++i) {
            number |= (data[3-i] & 0xff) << (i << 3);
        }
        return number;
    }
}
