package client.command;

import client.io.InputHandler;
import client.io.OutputHandler;
import client.transfer.Transfer;
import client.utils.PrettyPrint;
import server.database.UserData;

public abstract class CommandAbstract implements Command {
    protected OutputHandler output;
    protected InputHandler input;
    protected Transfer serverConnection;
    protected PrettyPrint prettyPrint;
    protected UserData userData;

    public CommandAbstract(OutputHandler outputHandler, Transfer transfer, UserData userData, InputHandler inputHandler) {
        output = outputHandler;
        input = inputHandler;
        serverConnection = transfer;
        prettyPrint = new PrettyPrint(output);
        this.userData = userData;
    }

}
