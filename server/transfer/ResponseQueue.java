package server.transfer;

import common.transfer.Request;
import common.transfer.Response;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.ReentrantLock;

public class ResponseQueue {
    final ReentrantLock lock;
    final Deque<Response> queue;

    public ResponseQueue() {
        lock = new ReentrantLock();
        queue = new ArrayDeque<>();
    }

    public void addLast(Response response) {
        lock.lock();
        queue.addLast(response);
        lock.unlock();
    }

    public Response pollFirst() {
        lock.lock();
        Response response = queue.pollFirst();
        lock.unlock();
        return response;
    }
}
