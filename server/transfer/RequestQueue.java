package server.transfer;

import common.transfer.Request;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.ReentrantLock;

public class RequestQueue {
    final ReentrantLock lock;
    final Deque<Request> queue;

    public RequestQueue() {
        lock = new ReentrantLock();
        queue = new ArrayDeque<>();
    }

    public void addLast(Request request) {
        lock.lock();
        queue.addLast(request);
        lock.unlock();
    }

    public Request pollFirst() {
        lock.lock();
        Request request = queue.pollFirst();
        lock.unlock();
        return request;
    }
}
