package client.command;

import client.io.InputHandler;
import client.io.OutputHandler;
import client.transfer.Transfer;
import server.database.UserData;

public class HelpCommand extends CommandAbstract {
    public HelpCommand(OutputHandler outputHandler, Transfer transfer, UserData userData, InputHandler inputHandler) {
        super(outputHandler, transfer, userData, inputHandler);
    }

    @Override
    public void execute() {
        output.printMessage("helpCommand_text");
    }
}
