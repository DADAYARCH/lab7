package client.command;

import client.io.InputHandler;
import client.io.OutputHandler;
import client.transfer.Transfer;
import common.CommandType;
import common.transfer.Request;
import common.transfer.Response;
import server.database.UserData;

public class RegisterCommand extends CommandAbstract {
    public RegisterCommand(OutputHandler outputHandler, Transfer transfer, UserData userData, InputHandler inputHandler) {
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
        UserData newUserData = new UserData(getName(), getPassword());
        Request request = new Request(newUserData, CommandType.REGISTER);
        serverConnection.send(request);
        Response response = serverConnection.receive();
        output.print(response.getMessage());
        userData.username = newUserData.username;
        userData.password = newUserData.password;
    }
}
