package server;

import common.transfer.ConnectionError;
import server.commands.CommandExecutor;
import server.database.DatabaseException;
import server.database.DatabaseHandler;
import server.database.WorkersDBManager;
import server.database.UserDBManager;;
import server.transfer.Receiver;
import server.transfer.RequestQueue;
import server.transfer.ResponseQueue;
import server.transfer.Sender;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Server {
    final static int DEFAULT_SERVER_PORT = 2548;
    private ExecutorService requestReceiverPool;
    private ExecutorService requestExecutorPool;
    private ForkJoinPool responseExecutorService;

    private final RequestQueue requestQueue;
    private final ResponseQueue responseQueue;

    private UserDBManager userDBManager;
    private WorkersDBManager workersDBManager;

    int lastPort = 2300;

    final DatagramSocket publicSocket;

    public Server(int serverPort) {
        try {
            publicSocket = new DatagramSocket(serverPort);
        } catch (SocketException e) {
            throw new ConnectionError("Недоступный порт");
        }
        requestReceiverPool = Executors.newFixedThreadPool(5);
        requestExecutorPool = Executors.newCachedThreadPool();
        responseExecutorService = new ForkJoinPool();

        requestQueue = new RequestQueue();
        responseQueue = new ResponseQueue();

        DatabaseHandler handler;
        try {
            handler = new DatabaseHandler();

        } catch (DatabaseException e) {
            throw new RuntimeException("cant connect to db");
        }
        userDBManager = new UserDBManager(handler);
        workersDBManager = new WorkersDBManager(handler, userDBManager);
    }

    public Server() {
        this(DEFAULT_SERVER_PORT);
    }

    public void start() {
        prepareExecutors();
        while (true) {
            SocketAddress clientAddress = getClientAddress();
            int port = getNextPort();
            while (true) {
                try {
                    requestReceiverPool.execute(new Receiver(port, requestQueue));
                    break;
                } catch (SocketException ignore) {}
            }
            sendPortToClient(clientAddress, port);
        }
    }

    private int getNextPort() {
        ++lastPort;
        if (lastPort == 3000) {
            lastPort = 2300;
        }
        return lastPort;
    }

    private SocketAddress getClientAddress() {
        try {
            DatagramPacket datagramPacket = new DatagramPacket(new byte[1], 1);
            publicSocket.receive(datagramPacket);
            return datagramPacket.getSocketAddress();
        } catch (IOException e) {
            return null;
        }
    }

    private void sendPortToClient(SocketAddress clientSocketAddress, int port) {
        try {
            DatagramPacket datagramPacket = new DatagramPacket(intToByteArray(port), 4, clientSocketAddress);
            publicSocket.send(datagramPacket);
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

    private void prepareExecutors() {
        for (int i = 0; i < 5; i++) {
            requestExecutorPool.execute(new CommandExecutor(requestQueue, responseQueue, userDBManager, workersDBManager));
            responseExecutorService.execute(new Sender(responseQueue));
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}