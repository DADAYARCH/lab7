package client.command;

import client.io.InputHandler;
import client.io.OutputHandler;
import client.transfer.Transfer;
import common.CommandType;
import common.transfer.Request;
import common.transfer.Response;
import server.database.UserData;

public class LoginCommand extends CommandAbstract {
    public LoginCommand(OutputHandler outputHandler, Transfer transfer, UserData userData, InputHandler inputHandler) {
        super(outputHandler, transfer, userData, inputHandler);
    }

    public String getName() {
        while (true) {
            output.printMessage("login");
            String username = input.nextLine();
            if (!username.isEmpty() && username.length() < 50) {
                return username;
            } else {
                output.printMessage("helpText_login");
            }
        }
    }

    public String getPassword() {
        while (true) {
            output.printMessage("password");
            String username = input.nextLine();
            if (!username.isEmpty() && username.length() < 50) {
                return username;
            } else {
                output.printMessage("helpText_password");
            }
        }
    }

    @Override
    public void execute() {
        userData.username = getName();
        userData.password = getPassword();
    }
}
