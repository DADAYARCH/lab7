package client.command;

import client.io.InputHandler;
import client.io.OutputHandler;
import client.transfer.Transfer;
import common.CommandType;
import common.transfer.Request;
import common.transfer.Response;
import server.database.UserData;

public class ShowCommand extends CommandAbstract {
    public ShowCommand(OutputHandler outputHandler, Transfer transfer, UserData userData, InputHandler inputHandler) {
        super(outputHandler, transfer, userData, inputHandler);
    }

    @Override
    public void execute() {
        Request request = new Request(userData, CommandType.GET_WORKERS);
        serverConnection.send(request);
        Response response = serverConnection.receive();
        if (response.getObject() == null) {
            output.print(response.getMessage());
        } else {
            prettyPrint.print(response.getObject());
        }

    }
}
