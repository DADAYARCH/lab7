package common.transfer;

import java.io.Serializable;
import java.net.SocketAddress;

public class Response implements Serializable {
    private SocketAddress clientAddress;
    private Object object;
    private String message;
    private int status;

    public Response(SocketAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Response(SocketAddress clientAddress, String message) {
        this.clientAddress = clientAddress;
        this.message = message;
    }

    public Response(SocketAddress clientAddress, String message, Object object) {
        this.clientAddress = clientAddress;
        this.message = message;
        this.object = object;
    }

    public SocketAddress getClientAddress() {
        return clientAddress;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return object;
    }
}
