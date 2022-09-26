package common.transfer;

import common.CommandType;
import common.models.Worker;
import server.database.UserData;

import java.io.Serializable;
import java.net.SocketAddress;

public class Request implements Serializable {
    private UserData userData;
    private CommandType commandType;
    private Worker worker;
    private SocketAddress from;

    public Request(UserData userData, CommandType commandType) {
        this.userData = userData;
        this.commandType = commandType;
    }

    public Request(UserData userData, CommandType commandType, Worker transferredObject) {
        this(userData, commandType);
        this.worker = transferredObject;
    }

    public UserData getUserData() {
        return userData;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public Worker getWorker() {
        return worker;
    }

    public SocketAddress getClientAddress() {
        return from;
    }

    public void setClientAddress(SocketAddress from) {
        this.from = from;
    }
}
